package ddd.caffeine.ratrip.module.travel_plan.application.day_schedule;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ddd.caffeine.ratrip.common.exception.domain.DayScheduleException;
import ddd.caffeine.ratrip.common.exception.domain.DaySchedulePlaceException;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedulePlace;

@Component
public class DaySchedulePlaceValidator {
	public void validateExist(boolean exist) {
		if (!exist) {
			throw new DaySchedulePlaceException(NOT_FOUND_DAY_SCHEDULE_PLACE_EXCEPTION);
		}
	}

	public void validateNotExist(boolean exist) {
		if (exist) {
			throw new DayScheduleException(ALREADY_EXIST_PLACE_IN_SCHEDULE_EXCEPTION);
		}
	}

	public DaySchedulePlace validateExistDaySchedulePlace(Optional<DaySchedulePlace> daySchedulePlace) {
		return daySchedulePlace.orElseThrow(
			() -> new DaySchedulePlaceException(NOT_FOUND_DAY_SCHEDULE_PLACE_EXCEPTION));
	}

	public void validateExchangeSequence(List<DaySchedulePlace> daySchedulePlaces) {
		if (daySchedulePlaces.size() != 2) {
			throw new DaySchedulePlaceException(NOT_FOUND_DAY_SCHEDULE_PLACE_EXCEPTION);
		}
	}
}
