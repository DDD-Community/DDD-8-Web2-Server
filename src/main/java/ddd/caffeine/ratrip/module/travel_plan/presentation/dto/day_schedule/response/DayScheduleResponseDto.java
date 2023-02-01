package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.response;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DayScheduleResponseDto {
	private UUID id;
	private List<DaySchedulePlaceDto> daySchedulePlaces;
	private boolean hasRegisteredPlace;

	@Builder
	public DayScheduleResponseDto(UUID dayScheduleUUID, List<DaySchedulePlaceDto> daySchedulePlaces,
		boolean hasRegisteredPlace) {
		this.id = dayScheduleUUID;
		this.daySchedulePlaces = daySchedulePlaces;
		this.hasRegisteredPlace = hasRegisteredPlace;
	}
}
