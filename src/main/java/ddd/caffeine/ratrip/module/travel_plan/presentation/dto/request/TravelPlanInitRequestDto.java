package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TravelPlanInitRequestDto {
	@NotEmpty(message = "Region must not be Blank")
	private String region;

	private LocalDate travelStartDate;

	@NotNull(message = "TravelDays must not be Null")
	private int travelDays;

	public TravelPlan mapByTravelPlan() {
		Region region = Region.createRegionIfNotExistReturnEtc(this.region);
		return TravelPlan.builder()
			.title(region + " " + "여행")
			.region(region)
			.startDate(travelStartDate)
			.travelDays(travelDays)
			.build();
	}
}
