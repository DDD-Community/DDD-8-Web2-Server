package ddd.caffeine.ratrip.module.travel_plan.application.day_schedule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedule;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedulePlace;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.DaySchedulePlaceRepository;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.dao.DaySchedulePlaceDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DaySchedulePlaceService {

	private final DaySchedulePlaceValidator daySchedulePlaceValidator;
	private final DaySchedulePlaceRepository daySchedulePlaceRepository;

	public List<DaySchedulePlaceDao> readDaySchedulePlaces(UUID dayScheduleUUID, String placeUUID) {
		return daySchedulePlaceRepository.findDaySchedulePlaceDaoByDayScheduleUUIDAndPlaceUUID(dayScheduleUUID,
			placeUUID);
	}

	public UUID addPlace(DaySchedule daySchedule, Place place, String memo) {
		validateAddPlaceInSchedule(daySchedule, place);
		DaySchedulePlace daySchedulePlace = DaySchedulePlace.builder()
			.daySchedule(daySchedule)
			.place(place)
			.sequence(readNextSequence(daySchedule.readPrimaryKey()))
			.memo(memo)
			.build();
		return daySchedulePlaceRepository.save(daySchedulePlace).getId();
	}

	public UUID update(String daySchedulePlaceUUID, String memo) {
		Optional<DaySchedulePlace> optionalDaySchedulePlace = daySchedulePlaceRepository.findById(
			UUID.fromString(daySchedulePlaceUUID));
		DaySchedulePlace daySchedulePlace = daySchedulePlaceValidator.validateExistDaySchedulePlace(
			optionalDaySchedulePlace);
		daySchedulePlace.update(memo);
		return daySchedulePlace.getId();
	}

	public void deletePlace(String daySchedulePlaceUUID) {
		boolean exist = daySchedulePlaceRepository.existByUUID(UUID.fromString(daySchedulePlaceUUID));
		daySchedulePlaceValidator.validateExist(exist);
		daySchedulePlaceRepository.delete(UUID.fromString(daySchedulePlaceUUID));
	}

	public void exchangePlaceSequence(List<UUID> daySchedulePlaceUUIDs) {
		List<DaySchedulePlace> daySchedulePlaces = daySchedulePlaceRepository.findDaySchedulePlacesById(
			daySchedulePlaceUUIDs.get(0), daySchedulePlaceUUIDs.get(1));
		daySchedulePlaceValidator.validateExchangeSequence(daySchedulePlaces);

		DaySchedulePlace baseDaySchedulePlace = daySchedulePlaces.get(0);
		baseDaySchedulePlace.exchangeOrder(daySchedulePlaces.get(1));
	}

	public String readRepresentativePhoto(UUID dayScheduleUUID) {
		return daySchedulePlaceRepository.findRepresentativeImageLink(dayScheduleUUID);
	}
	
	private int readNextSequence(UUID dayScheduleUUID) {
		int total = daySchedulePlaceRepository.countPlacesByDayScheduleUUID(dayScheduleUUID);
		return total + 1;
	}

	private void validateAddPlaceInSchedule(DaySchedule daySchedule, Place place) {
		boolean exist = daySchedulePlaceRepository.existByDayScheduleAndPlace(daySchedule, place);
		daySchedulePlaceValidator.validateNotExist(exist);
	}

}
