package ddd.caffeine.ratrip.module.place.service;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.PlaceException;
import ddd.caffeine.ratrip.module.place.feign.PlaceFeignService;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.PlaceKakaoModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.ImageNaverModel;
import ddd.caffeine.ratrip.module.place.model.Place;
import ddd.caffeine.ratrip.module.place.model.Region;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceDetailsResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PopularPlaceResponseDto;
import ddd.caffeine.ratrip.module.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceService {

	private final PlaceFeignService placeFeignService;
	private final PlaceValidator placeValidator;
	private final PlaceRepository placeRepository;

	@Transactional(readOnly = true)
	public PopularPlaceResponseDto readPopularPlaces(List<String> regions) {
		final int POPULAR_PLACE_COUNT = 10;
		List<Place> popularPlaces = placeRepository.findPopularPlacesInRegions(Region.createRegions(regions),
			POPULAR_PLACE_COUNT);
		return new PopularPlaceResponseDto(popularPlaces);
	}

	@Transactional(readOnly = true)
	public PlaceSearchResponseDto searchPlaces(String keyword, String latitude, String longitude, int page) {
		validateSearchRequestParameters(latitude, longitude, page);
		PlaceKakaoModel placeKakaoModel = placeFeignService.readPlacesByKeywordAndCoordinate(
			keyword, latitude, longitude, page);

		return placeKakaoModel.mapByPlaceSearchResponseDto();
	}

	@Transactional(readOnly = true)
	public PlaceDetailsResponseDto readPlaceDetailsByUUID(String uuid) {
		validateReadPlaceDetailsByUUIDParameters(uuid);
		Optional<Place> place = placeRepository.findById(UUID.fromString(uuid));
		place.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE_EXCEPTION));

		return new PlaceDetailsResponseDto(place.get());
	}

	@Transactional
	public PlaceDetailsResponseDto readPlaceDetailsByThirdPartyId(String thirdPartyId, String address,
		String placeName) {

		validatePlaceDetailsByThirdPartyIdParameters(thirdPartyId, address);
		Optional<Place> optionalPlace = placeRepository.findByKakaoId(thirdPartyId);

		// @TODO ????????? ?????? ??? ???????????? ??? ??? ?????????????????????.. ??? ???????????? ?????? -> ?????? ?????? ?????? ?????? ?????? ????????????.
		if (optionalPlace.isEmpty()) {
			Place place = readPlaceEntity(address, placeName);
			placeRepository.save(place);
			return new PlaceDetailsResponseDto(place);
		}

		Place place = optionalPlace.get();
		handlePlaceUpdate(place, address, placeName);
		return new PlaceDetailsResponseDto(place);
	}

	/**
	 * ?????? ????????? ???????????? ?????? ?????????.
	 */
	private void handlePlaceUpdate(Place place, String address, String placeName) {
		if (place.checkNeedsUpdate(address, placeName)) {
			PlaceKakaoModel placeKakaoModel = placeFeignService.readPlacesByAddressAndPlaceName(
				address, placeName);
			place.update(placeKakaoModel.readOne());
		}
	}

	/**
	 * ??????????????? ????????? ????????? ?????? ?????? Place Entity ??????????????? ?????????.
	 */
	private Place readPlaceEntity(String address, String placeName) {
		final int DATA_INDEX = 0;

		PlaceKakaoModel placeKakaoModel = placeFeignService.readPlacesByAddressAndPlaceName(address, placeName);
		Place place = placeKakaoModel.mapByPlaceEntity();

		ImageNaverModel imageModel = placeFeignService.readImageModel(placeName);
		place.injectImageLink(imageModel.readImageLinkByIndex(DATA_INDEX));

		return place;
	}

	private void validateSearchRequestParameters(String latitude, String longitude, int page) {
		placeValidator.validateRangeLatitude(latitude);
		placeValidator.validateRangeLongitude(longitude);
		placeValidator.validatePageSize(page);
	}

	private void validatePlaceDetailsByThirdPartyIdParameters(String thirdPartyId, String address) {
		placeValidator.validateIsNumber(thirdPartyId);
		//@Todo : ?????????????????? ????????????????????? ?????????.
	}

	private void validateReadPlaceDetailsByUUIDParameters(String uuid) {
		placeValidator.validateUUIDForm(uuid);
	}
}
