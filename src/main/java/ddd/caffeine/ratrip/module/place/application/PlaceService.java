package ddd.caffeine.ratrip.module.place.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.PlaceException;
import ddd.caffeine.ratrip.module.place.application.dto.BookmarkPlaceByRegionDto;
import ddd.caffeine.ratrip.module.place.application.dto.CategoryPlaceByCoordinateDto;
import ddd.caffeine.ratrip.module.place.application.dto.CategoryPlaceByRegionDto;
import ddd.caffeine.ratrip.module.place.application.dto.PlaceByCoordinateDto;
import ddd.caffeine.ratrip.module.place.application.dto.PlaceDetailDto;
import ddd.caffeine.ratrip.module.place.application.validator.PlaceValidator;
import ddd.caffeine.ratrip.module.place.domain.Address;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartySearchOption;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceRepository;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookmarkPlaceByRegionDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.CategoryPlaceByRegionDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.PlaceBookmarkDao;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignBlogModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignImageModel;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.BookmarkPlaceResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.BookmarkPlacesByCoordinateResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.BookmarkPlacesByRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.BookmarkResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.CategoryPlacesByCoordinateResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.CategoryPlacesByRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceDetailResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceInRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {
	static final long CACHE_EXPIRE_TIME = 60 * 60 * 24 * 7; // 7일

	private final PlaceFeignService placeFeignService;
	private final PlaceValidator placeValidator;
	private final BookmarkService bookmarkService;
	private final PlaceRepository placeRepository;

	private final RedisTemplate<String, Object> redisTemplate;

	public BookmarkPlacesByRegionResponseDto getBookmarkPlacesByRegion(final User user, final Region region,
		final Pageable pageable) {

		Slice<BookmarkPlaceByRegionDao> places = bookmarkService.getBookmarkPlacesByRegion(user, region, pageable);

		return BookmarkPlacesByRegionResponseDto.of(places.getContent(), places.hasNext());
	}

	public BookmarkPlacesByCoordinateResponseDto getBookmarkPlacesByCoordinate(final User user,
		final BookmarkPlaceByRegionDto request, final Pageable pageable) {

		Region region = placeFeignService.convertLongituteAndLatitudeToRegion(request.getLongitude(),
			request.getLatitude());

		Slice<BookmarkPlaceByRegionDao> places = bookmarkService.getBookmarkPlacesByRegion(user, region, pageable);

		return BookmarkPlacesByCoordinateResponseDto.of(places.getContent(), places.hasNext());
	}

	public CategoryPlacesByRegionResponseDto getCategoryPlacesByRegion(final CategoryPlaceByRegionDto request,
		final Pageable pageable) {

		Slice<CategoryPlaceByRegionDao> places = placeRepository.getCategoryPlacesByRegion(request.getRegion(),
			request.getCategory(), pageable);

		return CategoryPlacesByRegionResponseDto.of(places.getContent(), places.hasNext());
	}

	public CategoryPlacesByCoordinateResponseDto getCategoryPlacesByCoordinate(
		final CategoryPlaceByCoordinateDto request, final Pageable pageable) {

		Region region = placeFeignService.convertLongituteAndLatitudeToRegion(request.getLongitude(),
			request.getLatitude());

		Slice<CategoryPlaceByRegionDao> places = placeRepository.getCategoryPlacesByRegion(region,
			request.getCategory(), pageable);

		return CategoryPlacesByCoordinateResponseDto.of(places.getContent(), places.hasNext());
	}

	public PlaceInRegionResponseDto readPlacesInRegions(List<Region> regions, Pageable page) {
		Slice<PlaceBookmarkDao> places = placeRepository.findPlacesInRegions(regions, page);
		PlaceInRegionResponseDto response = new PlaceInRegionResponseDto(places.getContent(), places.hasNext());

		return response;
	}

	public PlaceSearchResponseDto searchPlaces(ThirdPartySearchOption searchOption) {
		FeignPlaceModel feignPlaceModel = placeFeignService.readPlacesByKeywordAndCoordinate(
			searchOption);

		return feignPlaceModel.mapByPlaceSearchResponseDto();
	}

	@Transactional
	public PlaceDetailResponseDto getPlaceDetail(User user, PlaceDetailDto request) {
		// 캐쉬에 있는지 확인
		String cacheKey = "place:" + request.getId();
		PlaceDetailResponseDto cache = (PlaceDetailResponseDto)redisTemplate.opsForValue().get(cacheKey);

		if (cache == null) {
			// 캐쉬에 없으면 API 콜 및 DB에 저장
			return saveAndCachePlaceDetail(request, cacheKey);
		}

		increaseViewCount(cache.getId());
		return cache;
	}

	private void increaseViewCount(UUID id) {
		Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE_EXCEPTION));
		place.increaseViewCount();
	}

	private PlaceDetailResponseDto saveAndCachePlaceDetail(PlaceDetailDto request, String cacheKey) {
		// API 콜
		Place place = findPlaceDetailFromKakao(request.getName(), request.getAddress());

		// DB에 없데이트
		place = updatePlace(request, place);

		// 캐시에 저장
		PlaceDetailResponseDto response = PlaceDetailResponseDto.of(place);
		redisTemplate.opsForValue().set(cacheKey, response, CACHE_EXPIRE_TIME, TimeUnit.MILLISECONDS);

		return response;
	}

	private Place updatePlace(PlaceDetailDto request, Place place) {
		Place originPlace = placeRepository.findByKakaoId(request.getId());

		if (originPlace == null) {
			return placeRepository.save(place);
		}

		originPlace.update(place);
		originPlace.increaseViewCount();

		return originPlace;
	}

	@Transactional
	public BookmarkResponseDto changeBookmarkState(User user, UUID id) {
		Optional<Place> place = placeRepository.findById(id);
		placeValidator.validateExistPlace(place);
		return bookmarkService.changeBookmarkState(user, place.get());
	}

	public BookmarkPlaceResponseDto readBookmarks(User user, List<Category> categories, Pageable pageable) {
		return bookmarkService.getBookmarks(user, categories, pageable);
	}

	public BookmarkResponseDto readBookmark(User user, UUID id) {
		Optional<Place> place = placeRepository.findById(id);
		placeValidator.validateExistPlace(place);
		return bookmarkService.readBookmark(user, place.get());
	}

	@Transactional
	public BookmarkResponseDto createBookmark(User user, UUID placeId) {
		Place place = readPlaceByUUID(placeId);
		return bookmarkService.createBookmark(user, place);
	}

	public Place readPlaceByUUID(UUID placeUUID) {
		Optional<Place> place = placeRepository.findById(placeUUID);
		return placeValidator.validateExistPlace(place);
	}

	public PlaceInRegionResponseDto readPlacesInCoordinate(PlaceByCoordinateDto request, Pageable pageable) {
		Region region = placeFeignService.convertLongituteAndLatitudeToRegion(request.getLongitude(),
			request.getLatitude());

		Slice<PlaceBookmarkDao> places = placeRepository.findPlacesInRegion(region, pageable);
		PlaceInRegionResponseDto response = new PlaceInRegionResponseDto(places.getContent(), places.hasNext());

		return response;
	}

	@Transactional
	public void deleteAllBookmark(User user) {
		bookmarkService.deleteAllBookmark(user);
	}

	/**
	 * 장소이름과 주소를 가지고 그에 맞는 Place Entity 생성해주는 메서드.
	 */
	private Place findPlaceDetailFromKakao(String name, String address) {
		FeignPlaceModel feignPlaceModel = placeFeignService.findPlaceDetailByNameAndAddress(name, address);
		Place place = feignPlaceModel.mapByPlaceEntity();
		setImageLinkInPlace(place, place.getName());
		//setBlogsInPlace(place, place.getName());

		return place;
	}

	private void setImageLinkInPlace(Place place, String keyword) {
		final int DATA_INDEX = 0;

		FeignImageModel imageModel = placeFeignService.readImageModel(keyword);
		place.setImageLink(imageModel.readImageLinkByIndex(DATA_INDEX));
	}

	private void setBlogsInPlace(Place place, String keyword) {
		FeignBlogModel blogModel = placeFeignService.readBlogModel(keyword);
		place.setBlogs(blogModel.readBlogs());
	}

	private boolean checkNeedsUpdate(PlaceBookmarkDao place, String placeName, String address) {
		Address checkAddress = new Address(address);
		if (place.getName().equals(placeName) && place.getAddress().equals(checkAddress)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}
