package ddd.caffeine.ratrip.module.memo.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.travel_plan.place.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationPathResponseDto {
	List<Place> places;

	public static RecommendationPathResponseDto of(List<Place> places) {
		return new RecommendationPathResponseDto(places);
	}
}
