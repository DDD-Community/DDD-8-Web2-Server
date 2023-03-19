package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PlaceDistanceResponse {
	private String name;
	private int distance;
}
