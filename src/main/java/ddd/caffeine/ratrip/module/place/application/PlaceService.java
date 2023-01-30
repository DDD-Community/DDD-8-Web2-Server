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
import ddd.caffeine.ratrip.common.secret.SecretKeyManager;
import ddd.caffeine.ratrip.common.util.HttpHeaderUtils;
import ddd.caffeine.ratrip.module.place.application.dto.BookmarkPlaceByRegionDto;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartyDetailSearchOption;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartySearchOption;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceRepository;
import ddd.caffeine.ratrip.module.place.external.KakaoRegionApiClient;
import ddd.caffeine.ratrip.module.place.external.dto.KakaoRegionResponse;
import ddd.caffeine.ratrip.module.place.feign.PlaceFeignService;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignBlogModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignImageModel;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceDetailResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceInRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceSaveThirdPartyResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlaceResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlacesByRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PlaceService {

	private final PlaceFeignService placeFeignService;
	private final PlaceValidator placeValidator;
	private final BookmarkService bookmarkService;
	private final PlaceRepository placeRepository;
	private final KakaoRegionApiClient kakaoRegionApiClient;
	private final SecretKeyManager secretKeyManager;

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

	public BookmarkPlacesByRegionResponseDto getBookmarkPlacesByRegion(User user, BookmarkPlaceByRegionDto request) {
		KakaoRegionResponse kakaoRegionResponse = changeLongitudeAndLatitudeToRegion(request);
		Region region = getRegionFromKakaoRegionResponse(kakaoRegionResponse);
		return bookmarkService.getBookmarkPlaceByRegion(user, region);
	}

	private KakaoRegionResponse changeLongitudeAndLatitudeToRegion(BookmarkPlaceByRegionDto request) {
		return kakaoRegionApiClient.getRegion(
			kakaoAuthorizationHeader(), request.getLongitude(),
			request.getLatitude());
	}

	private String kakaoAuthorizationHeader() {
		return HttpHeaderUtils.concatWithKakaoAKPrefix(secretKeyManager.getKakaoRestApiKey());
	}

	private static Region getRegionFromKakaoRegionResponse(KakaoRegionResponse kakaoRegionResponse) {
		return kakaoRegionResponse.getDocuments().get(0).getRegion_1depth_name();
	}

	/**
	 * 장소 데이터 업데이트 처리 메서드.
	 */
	private void handlePlaceUpdate(Place place, Map<String, String> placeNameAndAddressMap) {
		if (place.checkNeedsUpdate(placeNameAndAddressMap.get("address"), placeNameAndAddressMap.get("name"))) {
			FeignPlaceModel feignPlaceModel = placeFeignService.readPlacesByAddressAndPlaceName(
				placeNameAndAddressMap.get("address"), placeNameAndAddressMap.get("name"));
			place.update(feignPlaceModel.readOne());
			injectImageLinkInPlace(place, place.getName());
			injectBlogsInPlace(place, place.getName());
		}
	}

	/**
	 * 장소이름과 주소를 가지고 그에 맞는 Place Entity 생성해주는 메서드.
	 */
	private Place readPlaceEntity(Map<String, String> placeNameAndAddressMap) {
		FeignPlaceModel feignPlaceModel = placeFeignService.readPlacesByAddressAndPlaceName(
			placeNameAndAddressMap.get("address"), placeNameAndAddressMap.get("name"));
		Place place = feignPlaceModel.mapByPlaceEntity();

		injectImageLinkInPlace(place, place.getName());
		injectBlogsInPlace(place, place.getName());

		return place;
	}

	private void injectImageLinkInPlace(Place place, String keyword) {
		final int DATA_INDEX = 0;

		FeignImageModel imageModel = placeFeignService.readImageModel(keyword);
		place.injectImageLink(imageModel.readImageLinkByIndex(DATA_INDEX));
	}

	private void injectBlogsInPlace(Place place, String keyword) {
		FeignBlogModel blogModel = placeFeignService.readBlogModel(keyword);
		place.injectBlogs(blogModel.readBlogs());
	}

}
