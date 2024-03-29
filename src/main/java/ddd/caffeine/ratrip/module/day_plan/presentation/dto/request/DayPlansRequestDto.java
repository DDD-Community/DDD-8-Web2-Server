package ddd.caffeine.ratrip.module.day_plan.presentation.dto.request;

import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.day_plan.application.dto.DayPlansDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayPlansRequestDto {

	@NotNull(message = "TravelPlanId must not be Blank")
	private Long travelPlanId;

	public DayPlansDto toServiceDto() {
		return DayPlansDto.of(travelPlanId);
	}
}
