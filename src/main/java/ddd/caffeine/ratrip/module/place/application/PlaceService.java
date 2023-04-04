package ddd.caffeine.ratrip.module.place.application;

import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.module.place.application.dto.SearchPlaceDto;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceModel;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceSearchResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceService {
	private final PlaceFeignService placeFeignService;

	public PlaceSearchResponseDto searchPlaces(SearchPlaceDto request) {
		FeignPlaceModel feignPlaceModel = placeFeignService.findPlacesByKeywordAndCoordinate(request);

		return feignPlaceModel.mapByPlaceSearchResponseDto();
	}
}
