package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.response;

import java.util.UUID;

import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.dao.DaySchedulePlaceDao;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DaySchedulePlaceDto {
	private UUID id;
	private String memo;
	private int sequence;

	private PlaceModelInDaySchedule place;

	@Builder
	public DaySchedulePlaceDto(DaySchedulePlaceDao model) {
		this.id = model.getDaySchedulePlaceUUID();
		this.memo = model.getMemo();
		this.sequence = model.getSequence();
		this.place = PlaceModelInDaySchedule.builder()
			.id(model.getPlaceUUID())
			.name(model.getPlaceName())
			.category(model.getCategory())
			.location(model.getLocation())
			.build();
	}
}
