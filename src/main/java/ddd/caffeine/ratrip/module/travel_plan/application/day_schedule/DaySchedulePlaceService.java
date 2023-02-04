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

	public void updatePlacesSequence(UUID dayScheduleUUID, List<UUID> daySchedulePlaceUUIDs) {
		List<DaySchedulePlace> daySchedulePlaces = daySchedulePlaceRepository.findDaySchedulePlacesByDayScheduleUUID(
			dayScheduleUUID);
		for (int index = 0; index < daySchedulePlaceUUIDs.size(); index++) {
			UUID daySchedulePlaceUUID = daySchedulePlaceUUIDs.get(index);
			updateSequence(daySchedulePlaces, daySchedulePlaceUUID, index);
		}
	}

	public String readRepresentativePhoto(UUID dayScheduleUUID) {
		return daySchedulePlaceRepository.findRepresentativeImageLink(dayScheduleUUID);
	}

	private void updateSequence(List<DaySchedulePlace> daySchedulePlaces, UUID daySchedulePlaceUUID, int index) {
		Optional<DaySchedulePlace> optionalDaySchedulePlace = daySchedulePlaces.stream().filter(
			o -> o.getId().equals(daySchedulePlaceUUID)).findFirst();
		DaySchedulePlace daySchedulePlace = daySchedulePlaceValidator.validateExistDaySchedulePlace(
			optionalDaySchedulePlace);
		daySchedulePlace.changeSequence(index + 1);
	}

	private int readNextSequence(UUID dayScheduleUUID) {
		int total = daySchedulePlaceRepository.countPlacesByDayScheduleUUID(dayScheduleUUID);
		return total + 1;
	}

	private void validateAddPlaceInSchedule(DaySchedule daySchedule, Place place) {
		boolean exist = daySchedulePlaceRepository.existByDayScheduleAndPlace(daySchedule, place);
		daySchedulePlaceValidator.validateNotExist(exist);
	}

	/**
	 * Todo : 개발용 추후 삭제
	 */
	public void delete(UUID dayScheduleUUID) {
		List<DaySchedulePlace> daySchedulePlaces = daySchedulePlaceRepository.findByDayScheduleUUID(dayScheduleUUID);
		for (DaySchedulePlace daySchedulePlace : daySchedulePlaces) {
			daySchedulePlaceRepository.delete(daySchedulePlace);
		}
	}
}
