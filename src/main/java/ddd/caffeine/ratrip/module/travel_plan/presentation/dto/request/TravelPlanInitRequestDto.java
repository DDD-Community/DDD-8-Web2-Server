package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.common.RequestDataValidator;
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
		validateParameters(travelStartDate);
		Region region = Region.createRegionIfNotExistReturnEtc(this.region);
		return TravelPlan.builder()
			//Todo : title UI 에 대해서 디자인 물어보기. (임시 적용)
			.title(region + " " + "여행")
			.region(region)
			.startDate(travelStartDate)
			.travelDays(travelDays)
			.build();
	}

	private void validateParameters(LocalDate localDate) {
		RequestDataValidator.validateLocalDateForm(localDate.toString());
	}
}
