package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ShortestPathResponseDto {
	private List<PlaceDistanceResponse> placeDistanceResponses;

	public static ShortestPathResponseDto of(final List<PlaceDistanceResponse> placeDistanceResponses) {
		return new ShortestPathResponseDto(placeDistanceResponses);
	}
}
