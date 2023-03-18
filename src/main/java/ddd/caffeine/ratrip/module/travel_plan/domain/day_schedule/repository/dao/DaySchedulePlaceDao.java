package ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.dao;

import java.util.UUID;

import com.querydsl.core.annotations.QueryProjection;

import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Location;
import lombok.Getter;

@Getter
public class DaySchedulePlaceDao {
	private UUID daySchedulePlaceUUID;
	private String memo;
	private int sequence;
	private UUID placeUUID;
	private String placeName;
	private Category category;
	private Location location;

	@QueryProjection
	public DaySchedulePlaceDao(UUID daySchedulePlaceUUID, String memo, int sequence, UUID placeUUID, String placeName,
		Category category, Location location) {
		this.daySchedulePlaceUUID = daySchedulePlaceUUID;
		this.memo = memo;
		this.sequence = sequence;
		this.placeUUID = placeUUID;
		this.placeName = placeName;
		this.category = category;
		this.location = location;
	}
}
