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
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartyDetailSearchOption;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartySearchOption;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceRepository;
import ddd.caffeine.ratrip.module.place.feign.PlaceFeignService;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.PlaceKakaoModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.ImageNaverModel;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceInRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlaceResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.detail.PlaceDetailsResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.detail.PlaceSaveThirdPartyResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.search.PlaceSearchResponseDto;
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
		PlaceKakaoModel placeKakaoModel = placeFeignService.readPlacesByKeywordAndCoordinate(
			searchOption);

		return placeKakaoModel.mapByPlaceSearchResponseDto();
	}

	@Transactional(readOnly = true)
	public PlaceDetailsResponseDto readPlaceDetailsByUUID(String uuid, User user) {
		Place place = readPlaceByUUID(UUID.fromString(uuid));
		BookmarkResponseDto bookMarkModel = bookmarkService.readBookmarkModel(user, place);
		return new PlaceDetailsResponseDto(place, bookMarkModel);
	}

	@Transactional
	public PlaceSaveThirdPartyResponseDto savePlaceByThirdPartyData(ThirdPartyDetailSearchOption searchOption,
		User user) {
		BookmarkResponseDto bookmarkModel;
		Place place = placeRepository.findByThirdPartyID(searchOption.readThirdPartyId());

		if (place == null) {
			Place entity = readPlaceEntity(searchOption.readPlaceNameAndAddress());
			placeRepository.save(entity);
			bookmarkModel = bookmarkService.readBookmarkModel(user, entity);
			return new PlaceSaveThirdPartyResponseDto(entity, bookmarkModel);
		}

		handlePlaceUpdate(place, searchOption.readPlaceNameAndAddress());
		bookmarkModel = bookmarkService.readBookmarkModel(user, place);
		return new PlaceSaveThirdPartyResponseDto(place, bookmarkModel);
	}

	@Transactional
	public BookmarkResponseDto changeBookmarkState(UUID placeUUID, UUID bookmarkUUID) {
		Optional<Place> place = placeRepository.findById(placeUUID);
		placeValidator.validateExistPlace(place);
		return bookmarkService.changeBookmarkState(bookmarkUUID);
	}

	@Transactional(readOnly = true)
	public BookmarkPlaceResponseDto readBookmarks(User user, List<String> categories, Pageable pageable) {
		return bookmarkService.getBookmarks(user, categories, pageable);
	}

	public Place readPlaceByUUID(UUID placeUUID) {
		Optional<Place> place = placeRepository.findById(placeUUID);
		return placeValidator.validateExistPlace(place);
	}

	/**
	 * 장소 데이터 업데이트 처리 메서드.
	 */
	private void handlePlaceUpdate(Place place, Map<String, String> placeNameAndAddressMap) {
		if (place.checkNeedsUpdate(placeNameAndAddressMap.get("address"), placeNameAndAddressMap.get("name"))) {
			PlaceKakaoModel placeKakaoModel = placeFeignService.readPlacesByAddressAndPlaceName(
				placeNameAndAddressMap.get("address"), placeNameAndAddressMap.get("name"));
			place.update(placeKakaoModel.readOne());
		}
	}

	/**
	 * 장소이름과 주소를 가지고 그에 맞는 Place Entity 생성해주는 메서드.
	 */
	private Place readPlaceEntity(Map<String, String> placeNameAndAddressMap) {
		final int DATA_INDEX = 0;

		PlaceKakaoModel placeKakaoModel = placeFeignService.readPlacesByAddressAndPlaceName(
			placeNameAndAddressMap.get("address"), placeNameAndAddressMap.get("name"));
		Place place = placeKakaoModel.mapByPlaceEntity();

		ImageNaverModel imageModel = placeFeignService.readImageModel(placeNameAndAddressMap.get("name"));
		place.injectImageLink(imageModel.readImageLinkByIndex(DATA_INDEX));

		return place;
	}
}
