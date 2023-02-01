package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceInRegionResponseDto {
	private List<PlaceInRegionResponse> placeInRegionResponses;
	private boolean hasNext;

	public PlaceInRegionResponseDto(List<Place> places, boolean hasNext) {
		create(places);
		this.hasNext = hasNext;
	}

	private void create(List<Place> places) {
		this.placeInRegionResponses = new ArrayList<>();
		for (Place place : places) {
			placeInRegionResponses.add(new PlaceInRegionResponse(place));
		}
	}
}
