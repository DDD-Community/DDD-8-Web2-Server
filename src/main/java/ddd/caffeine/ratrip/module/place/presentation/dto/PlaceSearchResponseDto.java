package ddd.caffeine.ratrip.module.place.presentation.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PlaceSearchResponseDto {
	private List<PlaceSearchModel> placeSearchModels;

	public PlaceSearchResponseDto(List<PlaceSearchModel> placeSearchModels) {
		this.placeSearchModels = placeSearchModels;
	}
}
