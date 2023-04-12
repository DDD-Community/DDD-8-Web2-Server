package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.travel_plan.application.dto.CreateTravelPlanDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTravelPlanRequestDto {
	private Region region;

	private LocalDate travelStartDate;

	@NotNull(message = "TravelDays must not be Null")
	private int travelDays;

	public CreateTravelPlanDto toServiceDto() {
		return CreateTravelPlanDto.of(region, travelStartDate, travelDays);
	}
}
