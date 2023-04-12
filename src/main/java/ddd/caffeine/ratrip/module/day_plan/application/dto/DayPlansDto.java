package ddd.caffeine.ratrip.module.day_plan.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DayPlansDto {
	private final Long travelPlanId;

	public static DayPlansDto of(Long travelPlanId) {
		return new DayPlansDto(travelPlanId);
	}
}
