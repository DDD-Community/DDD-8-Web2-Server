package ddd.caffeine.ratrip.module.travel_plan.application.day_schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedule;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.DayScheduleRepository;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.dao.DaySchedulePlaceDao;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.response.DaySchedulePlaceDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.response.DayScheduleResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DayScheduleService {

	private final DaySchedulePlaceService daySchedulePlaceService;
	private final DayScheduleValidator dayScheduleValidator;
	private final DayScheduleRepository dayScheduleRepository;

	//todo : 개발용 
	public void TEMP_deleteDaySchedule(UUID travelPlanUUID) {
		List<DaySchedule> daySchedule = dayScheduleRepository.findByTravelPlanId(travelPlanUUID);
		for (DaySchedule schedule : daySchedule) {
			daySchedulePlaceService.delete(schedule.getId());
			dayScheduleRepository.delete(schedule);
		}
	}

	public void deleteDaySchedule(UUID travelPlanUUID) { //TODO - 쿼리 확인해보기
		List<DaySchedule> daySchedules = dayScheduleRepository.findByTravelPlanId(travelPlanUUID);

		for (DaySchedule daySchedule : daySchedules) {
			daySchedulePlaceService.deleteAllDaySchedulePlaceByDayScheduleUUID(daySchedule.getId());
			daySchedule.delete();
		}

	}

	public void initTravelPlan(TravelPlan travelPlan, List<LocalDate> dates) {
		List<DaySchedule> daySchedules = new ArrayList<>();
		for (LocalDate date : dates) {
			DaySchedule daySchedule = DaySchedule.builder()
				.travelPlan(travelPlan)
				.date(date)
				.build();
			daySchedules.add(daySchedule);
		}
		dayScheduleRepository.saveAll(daySchedules);
	}

	public UUID addPlace(UUID dayScheduleUUID, Place place, String memo) {
		Optional<DaySchedule> optionalDaySchedule = dayScheduleRepository.findById(dayScheduleUUID);
		DaySchedule daySchedule = dayScheduleValidator.validateExistOptionalDaySchedule(optionalDaySchedule);
		return daySchedulePlaceService.addPlace(daySchedule, place, memo);
	}

	public UUID updateDaySchedulePlace(String daySchedulePlaceUUID, String memo) {
		return daySchedulePlaceService.update(daySchedulePlaceUUID, memo);
	}

	public void deleteDaySchedulePlace(UUID dayScheduleUUID, String daySchedulePlaceUUID) {
		daySchedulePlaceService.deletePlace(dayScheduleUUID, daySchedulePlaceUUID);
	}

	public DayScheduleResponseDto readDaySchedule(UUID dayScheduleUUID, String placeUUID) {
		Optional<DaySchedule> daySchedule = dayScheduleRepository.findById(dayScheduleUUID);
		dayScheduleValidator.validateExistOptionalDaySchedule(daySchedule);

		List<DaySchedulePlaceDao> daySchedulePlaces = daySchedulePlaceService.readDaySchedulePlaces(
			daySchedule.get().getId(), placeUUID);

		return DayScheduleResponseDto.builder()
			.dayScheduleUUID(daySchedule.get().getId())
			.daySchedulePlaces(createDaySchedulePlaceDto(daySchedulePlaces))
			.hasRegisteredPlace(!(daySchedulePlaces.isEmpty()))
			.build();
	}

	public List<DaySchedule> readDaySchedulesInTravelPlan(UUID travelPlanUUID) {
		return dayScheduleRepository.findByTravelPlanId(travelPlanUUID);
	}

	public void updatePlacesSequence(UUID dayScheduleUUID, List<UUID> daySchedulePlaceUUIDs) {
		daySchedulePlaceService.updatePlacesSequence(dayScheduleUUID, daySchedulePlaceUUIDs);
	}

	public String readRepresentativePhoto(UUID travelPlanUUID, LocalDate date) {
		DaySchedule daySchedule = dayScheduleRepository.findByTravelPlanIdAndDate(travelPlanUUID, date);
		dayScheduleValidator.validateExistDaySchedule(daySchedule);
		return daySchedulePlaceService.readRepresentativePhoto(daySchedule.readPrimaryKey());
	}

	private List<DaySchedulePlaceDto> createDaySchedulePlaceDto(List<DaySchedulePlaceDao> daySchedulePlaceDaos) {
		List<DaySchedulePlaceDto> response = new ArrayList<>();
		for (DaySchedulePlaceDao dao : daySchedulePlaceDaos) {
			response.add(new DaySchedulePlaceDto(dao));
		}
		return response;
	}
}
