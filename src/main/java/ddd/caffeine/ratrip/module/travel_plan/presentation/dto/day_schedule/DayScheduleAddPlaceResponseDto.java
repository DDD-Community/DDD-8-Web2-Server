package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule;

import java.util.UUID;

import lombok.Getter;

@Getter
public class DayScheduleAddPlaceResponseDto {

	private UUID dayScheduleUUID;

	public DayScheduleAddPlaceResponseDto(UUID dayScheduleUUID) {
		this.dayScheduleUUID = dayScheduleUUID;
	}
}
