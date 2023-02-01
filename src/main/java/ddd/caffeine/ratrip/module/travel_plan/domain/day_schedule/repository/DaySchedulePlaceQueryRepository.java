package ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository;

import java.util.List;
import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedule;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedulePlace;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.dao.DaySchedulePlaceDao;

public interface DaySchedulePlaceQueryRepository {
	List<DaySchedulePlaceDao> findDaySchedulePlaceDaoByDayScheduleUUIDAndPlaceUUID(UUID dayScheduleUUID,
		String placeUUID);

	List<DaySchedulePlace> findDaySchedulePlacesById(UUID firstUUID, UUID secondUUID);

	Integer countPlacesByDayScheduleUUID(UUID dayScheduleUUID);

	boolean existByDayScheduleAndPlace(DaySchedule daySchedule, Place place);

	boolean existByUUID(UUID daySchedulePlaceUUID);

	String findRepresentativeImageLink(UUID dayScheduleUUID);

	Long delete(UUID daySchedulePlaceUUID);
}
