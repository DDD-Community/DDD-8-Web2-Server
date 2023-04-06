package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import java.time.LocalDate;

import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTravelPlanResponseDto {
	private Long id;
	private String title;
	private Region region;
	private LocalDate startDate;
	private int travelDays;
	private boolean isEnd;

	public static CreateTravelPlanResponseDto of(TravelPlan travelPlan) {
		return new CreateTravelPlanResponseDto(travelPlan.getId(), travelPlan.getTitle(), travelPlan.getRegion(),
			travelPlan.getStartDate(), travelPlan.getTravelDays(), travelPlan.isEnd());
	}
}
