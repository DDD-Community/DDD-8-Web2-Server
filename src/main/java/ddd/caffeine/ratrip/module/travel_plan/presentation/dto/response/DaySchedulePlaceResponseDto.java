package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class DaySchedulePlaceResponseDto {

	private UUID id;

	public DaySchedulePlaceResponseDto(UUID daySchedulePlaceUUID) {
		this.id = daySchedulePlaceUUID;
	}
}
