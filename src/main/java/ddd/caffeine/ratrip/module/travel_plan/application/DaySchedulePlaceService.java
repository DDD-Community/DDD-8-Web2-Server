package ddd.caffeine.ratrip.module.travel_plan.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.common.exception.domain.DayScheduleException;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedule;
import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedulePlace;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.DaySchedulePlaceDao;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.day_schedule.DaySchedulePlaceRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DaySchedulePlaceService {

	private final DaySchedulePlaceRepository daySchedulePlaceRepository;

	public List<DaySchedulePlaceDao> readDaySchedulePlaces(UUID dayScheduleUUID) {
		return daySchedulePlaceRepository.findDaySchedulePlaceDaoByDayScheduleUUID(dayScheduleUUID);
	}

	public UUID addPlace(DaySchedule daySchedule, Place place, String memo) {
		validateAddPlaceInSchedule(daySchedule, place);
		DaySchedulePlace daySchedulePlace = DaySchedulePlace.builder()
			.daySchedule(daySchedule)
			.place(place)
			.sequence(readNextSequence(daySchedule.readPrimaryKey()))
			.memo(memo)
			.build();
		daySchedulePlaceRepository.save(daySchedulePlace);
		return daySchedule.readPrimaryKey();
	}

	public void exchangePlaceSequence(UUID dayScheduleUUID, List<UUID> placeUUIDs) {
		List<DaySchedulePlace> daySchedulePlaces = daySchedulePlaceRepository.findByDayScheduleUUIDAndPlaceUUIDs(
			dayScheduleUUID, placeUUIDs.get(0), placeUUIDs.get(1));

		DaySchedulePlace baseDaySchedulePlace = daySchedulePlaces.get(0);
		baseDaySchedulePlace.exchangeOrder(daySchedulePlaces.get(1));
	}

	private int readNextSequence(UUID dayScheduleUUID) {
		int total = daySchedulePlaceRepository.countPlacesByDayScheduleUUID(dayScheduleUUID);
		return total + 1;
	}

	private void validateAddPlaceInSchedule(DaySchedule daySchedule, Place place) {
		boolean exist = daySchedulePlaceRepository.existByDayScheduleAndPlace(daySchedule, place);
		if (exist) {
			throw new DayScheduleException(ALREADY_EXIST_PLACE_IN_SCHEDULE_EXCEPTION);
		}
	}
}
