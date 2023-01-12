package ddd.caffeine.ratrip.module.place.presentation.dto.popular;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.place.model.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PopularPlaceResponseDto {
	private List<PopularPlaceModel> popularPlaceModels;

	public PopularPlaceResponseDto(List<Place> places) {
		create(places);
	}

	private void create(List<Place> places) {
		this.popularPlaceModels = new ArrayList<>();
		for (Place place : places) {
			popularPlaceModels.add(new PopularPlaceModel(place));
		}
	}
}
