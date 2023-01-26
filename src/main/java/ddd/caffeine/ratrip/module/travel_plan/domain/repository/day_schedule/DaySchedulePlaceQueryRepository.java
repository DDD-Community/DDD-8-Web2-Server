package ddd.caffeine.ratrip.module.travel_plan.domain.repository.day_schedule;

import java.util.List;
import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedule;
import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedulePlace;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.DaySchedulePlaceDao;

public interface DaySchedulePlaceQueryRepository {
	List<DaySchedulePlaceDao> findDaySchedulePlaceDaoByDayScheduleUUID(UUID dayScheduleUUID);

	List<DaySchedulePlace> findByDayScheduleUUIDAndPlaceUUIDs(UUID dayScheduleUUID, UUID firstPlaceUUID,
		UUID secondPlaceUUID);

	Integer countPlacesByDayScheduleUUID(UUID dayScheduleUUID);

	boolean existByDayScheduleAndPlace(DaySchedule daySchedule, Place place);
}
