package ddd.caffeine.ratrip.module.place.presentation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.place.presentation.dto.detail.PlaceDetailsByThirdPartyRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.detail.PlaceDetailsByUUIDRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.detail.PlaceDetailsResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.popular.PopularPlaceResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.search.PlaceSearchRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.search.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 장소 추천 API
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/place")
public class PlaceController {
	private final PlaceService placeService;

	@GetMapping("search")
	public ResponseEntity<PlaceSearchResponseDto> callPlaceSearchApi(
		@Valid @ModelAttribute PlaceSearchRequestDto request) {

		PlaceSearchResponseDto response = placeService.searchPlaces(request.mapByThirdPartySearchOption());
		return ResponseEntity.ok(response);
	}

	@GetMapping("third-party-id")
	public ResponseEntity<PlaceDetailsResponseDto> callPlaceDetailsApiByThirdPartyId(
		@Valid @ModelAttribute PlaceDetailsByThirdPartyRequestDto request) {

		PlaceDetailsResponseDto response = placeService.readPlaceDetailsByThirdPartyId(
			request.getId(), request.getAddress(), request.getPlaceName());

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<PlaceDetailsResponseDto> callPlaceDetailsApiByUUID(
		@Valid @ModelAttribute PlaceDetailsByUUIDRequestDto request) {

		PlaceDetailsResponseDto response = placeService.readPlaceDetailsByUUID(request.getId());
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "recommend")
	public ResponseEntity<PopularPlaceResponseDto> callPopularPlacesApi(
		@RequestParam(name = "region", required = false, defaultValue = "전국") List<String> regions) {

		PopularPlaceResponseDto response = placeService.readPopularPlaces(regions);
		return ResponseEntity.ok(response);
	}
}
