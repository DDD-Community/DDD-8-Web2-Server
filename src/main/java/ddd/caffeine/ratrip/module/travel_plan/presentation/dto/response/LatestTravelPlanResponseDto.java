package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LatestTravelPlanResponseDto {

	private TravelPlanResponseDto content;
	private boolean hasPlan;

	public LatestTravelPlanResponseDto(boolean hasPlan) {
		this.hasPlan = hasPlan;
	}

	@Builder
	public LatestTravelPlanResponseDto(TravelPlanResponseDto content, boolean hasPlan) {
		this.content = content;
		this.hasPlan = hasPlan;
	}
}
