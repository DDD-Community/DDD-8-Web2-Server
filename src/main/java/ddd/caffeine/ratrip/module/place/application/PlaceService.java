package ddd.caffeine.ratrip.module.place.application;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.place.application.dto.BookmarkPlaceByRegionDto;
import ddd.caffeine.ratrip.module.place.application.dto.CategoryPlaceByCoordinateDto;
import ddd.caffeine.ratrip.module.place.application.dto.CategoryPlaceByRegionDto;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartyDetailSearchOption;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartySearchOption;
import ddd.caffeine.ratrip.module.place.domain.bookmark.repository.dao.BookmarkPlaceByRegionDao;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceRepository;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.CategoryPlaceByRegionDao;
import ddd.caffeine.ratrip.module.place.feign.PlaceFeignService;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignBlogModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignImageModel;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlaceResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlacesByCoordinateResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlacesByRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.CategoryPlacesByCoordinateResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.CategoryPlacesByRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceDetailResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceInRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceSaveThirdPartyResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PlaceService {

	private final PlaceFeignService placeFeignService;
	private final PlaceValidator placeValidator;
	private final BookmarkService bookmarkService;
	private final PlaceRepository placeRepository;

	@Transactional(readOnly = true)
	public PlaceInRegionResponseDto readPlacesInRegions(List<String> regions, Pageable page) {

		Slice<Place> places = placeRepository.findPlacesInRegions(Region.createRegions(regions), page);
		return new PlaceInRegionResponseDto(places.getContent(), places.hasNext());
	}

	@Transactional(readOnly = true)
	public PlaceSearchResponseDto searchPlaces(ThirdPartySearchOption searchOption) {

		FeignPlaceModel feignPlaceModel = placeFeignService.readPlacesByKeywordAndCoordinate(
			searchOption);
		return feignPlaceModel.mapByPlaceSearchResponseDto();
	}

	@Transactional(readOnly = true)
	public PlaceDetailResponseDto readPlaceDetailsByUUID(String uuid) {

		Place place = readPlaceByUUID(UUID.fromString(uuid));
		return new PlaceDetailResponseDto(place);
	}

	@Transactional
	public PlaceSaveThirdPartyResponseDto savePlaceByThirdPartyData(ThirdPartyDetailSearchOption searchOption) {
		Place place = placeRepository.findByThirdPartyID(searchOption.readThirdPartyId());

		if (place == null) {
			Place entity = readPlaceEntity(searchOption.readPlaceNameAndAddress());
			placeRepository.save(entity);
			return new PlaceSaveThirdPartyResponseDto(entity);
		}
		handlePlaceUpdate(place, searchOption.readPlaceNameAndAddress());
		return new PlaceSaveThirdPartyResponseDto(place);
	}

	@Transactional
	public BookmarkResponseDto changeBookmarkState(User user, String placeUUID) {
		Optional<Place> place = placeRepository.findById(UUID.fromString(placeUUID));
		placeValidator.validateExistPlace(place);
		return bookmarkService.changeBookmarkState(user, place.get());
	}

	@Transactional(readOnly = true)
	public BookmarkPlaceResponseDto readBookmarks(User user, List<String> categories, Pageable pageable) {
		return bookmarkService.getBookmarks(user, categories, pageable);
	}

	@Transactional(readOnly = true)
	public BookmarkResponseDto readBookmark(User user, String placeUUID) {
		Optional<Place> place = placeRepository.findById(UUID.fromString(placeUUID));
		placeValidator.validateExistPlace(place);
		return bookmarkService.readBookmark(user, place.get());
	}

	@Transactional
	public BookmarkResponseDto createBookmark(User user, String placeUUID) {
		Place place = readPlaceByUUID(UUID.fromString(placeUUID));
		return bookmarkService.createBookmark(user, place);
	}

	public Place readPlaceByUUID(UUID placeUUID) {
		Optional<Place> place = placeRepository.findById(placeUUID);
		return placeValidator.validateExistPlace(place);
	}

	@Transactional(readOnly = true)
	public BookmarkPlacesByRegionResponseDto getBookmarkPlacesByRegion(User user, Region region,
		Pageable pageable) {

		Slice<BookmarkPlaceByRegionDao> places = bookmarkService.getBookmarkPlacesByRegion(user, region, pageable);

		return new BookmarkPlacesByRegionResponseDto(places.getContent(), places.hasNext());
	}

	@Transactional(readOnly = true)
	public BookmarkPlacesByCoordinateResponseDto getBookmarkPlacesByCoordinate(User user,
		BookmarkPlaceByRegionDto request, Pageable pageable) {

		Region region = placeFeignService.convertLongituteAndLatitudeToRegion(request.getLongitude(),
			request.getLatitude());

		Slice<BookmarkPlaceByRegionDao> places = bookmarkService.getBookmarkPlacesByRegion(user, region, pageable);

		return new BookmarkPlacesByCoordinateResponseDto(places.getContent(), places.hasNext());
	}

	@Transactional(readOnly = true)
	public CategoryPlacesByRegionResponseDto getCategoryPlacesByRegion(User user, CategoryPlaceByRegionDto request,
		Pageable pageable) {

		Slice<CategoryPlaceByRegionDao> places = placeRepository.getCategoryPlacesByRegion(user, request.getRegion(),
			request.getCategory(), pageable);

		return new CategoryPlacesByRegionResponseDto(places.getContent(), places.hasNext());
	}

	@Transactional(readOnly = true)
	public CategoryPlacesByCoordinateResponseDto getCategoryPlacesByCoordinate(User user,
		CategoryPlaceByCoordinateDto request, Pageable pageable) {

		Region region = placeFeignService.convertLongituteAndLatitudeToRegion(request.getLongitude(),
			request.getLatitude());

		Slice<CategoryPlaceByRegionDao> places = placeRepository.getCategoryPlacesByRegion(user, region,
			request.getCategory(), pageable);

		return new CategoryPlacesByCoordinateResponseDto(places.getContent(), places.hasNext());
	}

	/**
	 * 장소 데이터 업데이트 처리 메서드.
	 */
	private void handlePlaceUpdate(Place place, Map<String, String> placeNameAndAddressMap) {
		if (place.checkNeedsUpdate(placeNameAndAddressMap.get("address"), placeNameAndAddressMap.get("name"))) {
			FeignPlaceModel feignPlaceModel = placeFeignService.readPlacesByAddressAndPlaceName(
				placeNameAndAddressMap.get("address"), placeNameAndAddressMap.get("name"));
			place.update(feignPlaceModel.readOne());
			setImageLinkInPlace(place, place.getName());
			setBlogsInPlace(place, place.getName());
		}
	}

	/**
	 * 장소이름과 주소를 가지고 그에 맞는 Place Entity 생성해주는 메서드.
	 */
	private Place readPlaceEntity(Map<String, String> placeNameAndAddressMap) {
		FeignPlaceModel feignPlaceModel = placeFeignService.readPlacesByAddressAndPlaceName(
			placeNameAndAddressMap.get("address"), placeNameAndAddressMap.get("name"));
		Place place = feignPlaceModel.mapByPlaceEntity();

		setImageLinkInPlace(place, place.getName());
		setBlogsInPlace(place, place.getName());

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
}
