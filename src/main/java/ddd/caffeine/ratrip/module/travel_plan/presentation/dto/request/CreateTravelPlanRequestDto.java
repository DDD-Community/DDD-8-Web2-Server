package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.travel_plan.application.dto.CreateTravelPlanDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTravelPlanRequestDto {
	@NotEmpty(message = "Region must not be Blank")
	private Region region;

	private LocalDate travelStartDate;

	@NotNull(message = "TravelDays must not be Null")
	private int travelDays;

	public CreateTravelPlanDto toServiceDto() {
		return CreateTravelPlanDto.of(region, travelStartDate, travelDays);
	}
}
