package ddd.caffeine.ratrip.module.place.presentation.dto.search;

import java.util.List;

import lombok.Getter;

@Getter
public class PlaceSearchResponseDto {
	private List<PlaceSearchModel> placeSearchModels;

	public PlaceSearchResponseDto(List<PlaceSearchModel> placeSearchModels) {
		this.placeSearchModels = placeSearchModels;
	}
}
