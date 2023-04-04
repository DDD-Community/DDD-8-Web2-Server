package ddd.caffeine.ratrip.module.day_schedule.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.PlaceNameResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendationPathResponseDto {
	private List<PlaceNameResponse> placeNameResponses;

	public static RecommendationPathResponseDto of(final List<PlaceNameResponse> placeNameResponses) {
		return new RecommendationPathResponseDto(placeNameResponses);
	}
}
