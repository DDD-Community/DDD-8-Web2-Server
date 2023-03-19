package ddd.caffeine.ratrip.module.travel_plan.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.TravelPlanException;
import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.application.validator.TravelPlanValidator;
import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedule;
import ddd.caffeine.ratrip.module.travel_plan.domain.DayScheduleAccessOption;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanAccessOption;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanUser;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanRepository;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.DayScheduleInTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.DaySchedulePlaceResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.DayScheduleResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.LatestTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.MyTravelPlanResponse;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.MyTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.TravelPlanResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelPlanService {
	private final TravelPlanUserService travelPlanUserService;
	private final TravelPlanValidator travelPlanValidator;
	private final DayScheduleService dayScheduleService;
	private final PlaceService placeService;
	private final TravelPlanRepository travelPlanRepository;

	@Transactional
	public void deleteAllTravelPlan(User user) {
		List<TravelPlanUser> travelPlanUsers = travelPlanUserService.findByUser(user);

		for (TravelPlanUser travelPlanUser : travelPlanUsers) {
			deleteDaySchedule(travelPlanUser);
			deleteTravelPlanUser(travelPlanUser);
			deleteTravelPlan(travelPlanUser.getTravelPlan().readUUID());
		}
	}

	private void deleteTravelPlanUser(TravelPlanUser travelPlanUser) {
		travelPlanUserService.deleteTravelPlanUser(travelPlanUser);
	}

	private void deleteDaySchedule(TravelPlanUser travelPlanUser) {
		dayScheduleService.deleteDaySchedule(travelPlanUser.getTravelPlan().readUUID());
	}

	@Transactional
	public void deleteTravelPlan(UUID TravelPlanUUID) {
		TravelPlan travelPlan = travelPlanRepository.findById(TravelPlanUUID)
			.orElseThrow(() -> new TravelPlanException(NOT_FOUND_TRAVEL_PLAN_EXCEPTION));

		travelPlan.delete();
	}

	@Transactional(readOnly = true)
	public LatestTravelPlanResponseDto readRecentTravelPlanByUser(User user) {
		TravelPlanUser travelPlanUser = travelPlanUserService.readByUserLatestTravel(user);
		//최근 작성한 여행 없을 경우,
		if (travelPlanUser == null) {
			return new LatestTravelPlanResponseDto(Boolean.FALSE);
		}
		//작성중인 여행이 있을 경우,
		return LatestTravelPlanResponseDto.builder()
			.content(new TravelPlanResponseDto(travelPlanUser.readTravelPlan()))
			.hasPlan(Boolean.TRUE)
			.build();
	}

	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	public void endOutOfDateTravelPlan() {
		LocalDate currentDate = LocalDate.now();
		List<TravelPlan> travelPlans = travelPlanRepository.findAllOngoingTravelPlan();
		for (TravelPlan travelPlan : travelPlans) {
			if (filterOutOfDate(travelPlan, currentDate)) {
				travelPlan.endTheTrip();
			}
		}
	}

	@Transactional(readOnly = true)
	public MyTravelPlanResponseDto readAllTravelPlanByUserPagination(User user, Pageable pageable) {
		MyTravelPlanResponseDto response = travelPlanUserService.readByUserPagination(user, pageable);
		for (MyTravelPlanResponse content : response.readContents()) {
			String imageLink = dayScheduleService.readRepresentativePhoto(content.readTravelPlanId(),
				content.readStartDate());
			content.setRepresentativePhoto(imageLink);
		}
		return response;
	}

	@Transactional
	public TravelPlanResponseDto makeTravelPlan(TravelPlan travelPlan, User user) {
		//진행중인 일정 있을 경우 예외
		travelPlanUserService.validateMakeTravelPlan(user);
		//TravelPlan 생성 및 저장
		travelPlanRepository.save(travelPlan);
		//TravelPlan 및 User 저장.
		travelPlanUserService.saveTravelPlanWithUser(travelPlan, user);
		//daySchedule 생성 및 저장.
		dayScheduleService.initTravelPlan(travelPlan, createDateList(travelPlan.getStartDate(),
			travelPlan.getTravelDays()));
		return new TravelPlanResponseDto(travelPlan);
	}

	@Transactional
	public TravelPlanResponseDto endTravelPlan(TravelPlanAccessOption accessOption) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption);
		//객체 가져오기
		TravelPlan travelPlan = travelPlanValidator.validateExistTravelPlan(
			travelPlanRepository.findById(accessOption.readTravelPlanUUID()));
		//변경
		travelPlan.endTheTrip();
		return new TravelPlanResponseDto(travelPlan);
	}

	@Transactional(readOnly = true)
	public DayScheduleResponseDto readScheduleByUUID(DayScheduleAccessOption accessOption, String placeUUID) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption.readTravelPlanAccessOption());
		return dayScheduleService.readDaySchedule(accessOption.readDayScheduleUUID(), placeUUID);
	}

	@Transactional(readOnly = true)
	public DayScheduleInTravelPlanResponseDto readDaySchedulesInTravelPlan(TravelPlanAccessOption accessOption) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption);
		List<DaySchedule> daySchedules = dayScheduleService.readDaySchedulesInTravelPlan(
			accessOption.readTravelPlanUUID());

		return new DayScheduleInTravelPlanResponseDto(daySchedules);
	}

	@Transactional
	public DaySchedulePlaceResponseDto addPlaceInDaySchedule(DayScheduleAccessOption accessOption, String placeUUID,
		String memo) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption.readTravelPlanAccessOption());
		//장소 불러오기
		Place place = placeService.readPlaceByUUID(UUID.fromString(placeUUID));
		//저장하기
		UUID daySchedulePlaceUUID = dayScheduleService.addPlace(accessOption.readDayScheduleUUID(), place, memo);

		return new DaySchedulePlaceResponseDto(daySchedulePlaceUUID);
	}

	@Transactional
	public DaySchedulePlaceResponseDto updatePlaceInDaySchedule(DayScheduleAccessOption accessOption,
		String daySchedulePlaceUUID, String memo) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption.readTravelPlanAccessOption());
		//업데이트
		UUID updatedDaySchedulePlaceUUID = dayScheduleService.updateDaySchedulePlace(daySchedulePlaceUUID, memo);
		return new DaySchedulePlaceResponseDto(updatedDaySchedulePlaceUUID);
	}

	@Transactional
	public void deletePlaceInDaySchedule(DayScheduleAccessOption accessOption, String daySchedulePlaceUUID) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption.readTravelPlanAccessOption());
		//삭제
		dayScheduleService.deleteDaySchedulePlace(accessOption.readDayScheduleUUID(), daySchedulePlaceUUID);
	}

	@Transactional
	public void updatePlacesSequenceInDaySchedule(DayScheduleAccessOption accessOption,
		List<UUID> daySchedulePlaceUUIDs) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption.readTravelPlanAccessOption());
		//하루 일정 장소 순서 update
		dayScheduleService.updatePlacesSequence(accessOption.readDayScheduleUUID(), daySchedulePlaceUUIDs);
	}

	private List<LocalDate> createDateList(LocalDate startTravelDate, int travelDays) {
		List<LocalDate> dates = new ArrayList<>();
		for (int i = 0; i < travelDays; i++) {
			LocalDate localDate = startTravelDate.plusDays(i);
			dates.add(localDate);
		}
		return dates;
	}

	private boolean filterOutOfDate(TravelPlan travelPlan, LocalDate currentDate) {
		LocalDate endDate = travelPlan.getStartDate().plusDays(travelPlan.getTravelDays() - 1);
		return endDate.isBefore(currentDate);
	}
}
