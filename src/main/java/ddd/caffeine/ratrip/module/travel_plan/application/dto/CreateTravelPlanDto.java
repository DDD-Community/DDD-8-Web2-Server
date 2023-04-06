package ddd.caffeine.ratrip.module.travel_plan.application.dto;

import java.time.LocalDate;

import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.place.domain.Region;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateTravelPlanDto {
	private Region region;
	private LocalDate travelStartDate;
	private int travelDays;

	public static CreateTravelPlanDto of(Region region, LocalDate travelStartDate, int travelDays) {
		return new CreateTravelPlanDto(region, travelStartDate, travelDays);
	}

	public TravelPlan toEntity(User user) {
		return TravelPlan.of(region + " " + "여행", region, travelDays, travelStartDate, user);
	}
}
