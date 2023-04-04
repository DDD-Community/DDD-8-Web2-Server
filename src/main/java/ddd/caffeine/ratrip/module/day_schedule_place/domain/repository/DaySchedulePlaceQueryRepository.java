package ddd.caffeine.ratrip.module.day_schedule_place.domain.repository;

import java.util.List;
import java.util.UUID;

import ddd.caffeine.ratrip.module.day_schedule.domain.DaySchedule;
import ddd.caffeine.ratrip.module.day_schedule.domain.respository.dao.PlaceNameLongitudeLatitudeDao;
import ddd.caffeine.ratrip.module.day_schedule_place.domain.DaySchedulePlace;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.DaySchedulePlaceDao;

public interface DaySchedulePlaceQueryRepository {

	List<PlaceNameLongitudeLatitudeDao> findPlacesNameLongitudeLatitudeById(UUID id);

	List<DaySchedulePlaceDao> findDaySchedulePlaceDaoByDayScheduleUUIDAndPlaceUUID(UUID dayScheduleUUID,
		String placeUUID);

	List<DaySchedulePlace> findDaySchedulePlacesByDayScheduleUUID(UUID dayScheduleUUID);

	Integer countPlacesByDayScheduleUUID(UUID dayScheduleUUID);

	boolean existByDayScheduleAndPlace(DaySchedule daySchedule, Place place);

	List<DaySchedulePlace> findByDaySchedulePlaceGreaterThanSequence(UUID daySchedulePlaceUUID, int sequence);

	String findRepresentativeImageLink(UUID dayScheduleUUID);

	Long delete(UUID daySchedulePlaceUUID);

	List<DaySchedulePlace> findByDayScheduleUUID(UUID dayScheduleUUID);
}
