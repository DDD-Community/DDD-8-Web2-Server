package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PlaceNameResponse {
	private String name;

	public static PlaceNameResponse of(final String name) {
		return new PlaceNameResponse(name);
	}
}
