package ddd.caffeine.ratrip.module.travel_plan.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.DayScheduleAccessOption;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanAccessOption;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanUser;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanRepository;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.LocalDateDao;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.TravelPlanOngoingResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.TravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.TravelPlanResponseModel;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.DayScheduleAddPlaceResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.DayScheduleResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelPlanService {
	private final TravelPlanUserService travelPlanUserService;
	private final DayScheduleService dayScheduleService;
	private final PlaceService placeService;
	private final TravelPlanRepository travelPlanRepository;

	@Transactional(readOnly = true)
	public TravelPlanOngoingResponseDto readTravelPlanByUser(User user) {
		TravelPlanUser travelPlanUser = travelPlanUserService.readByUserUnfinishedTravel(user);
		//작성중인 여행 없을 경우,
		if (travelPlanUser == null) {
			return new TravelPlanOngoingResponseDto(Boolean.FALSE);
		}
		//작성중인 여행이 있을 경우,
		return TravelPlanOngoingResponseDto.builder()
			.content(new TravelPlanResponseModel(travelPlanUser.readTravelPlan()))
			.hasPlan(Boolean.TRUE)
			.build();
	}

	@Transactional(readOnly = true)
	public TravelPlanResponseDto readAllTravelPlanByUser(User user, Pageable pageable) {
		return travelPlanUserService.readByUser(user, pageable);
	}

	@Transactional
	public TravelPlanResponseModel makeTravelPlan(TravelPlan travelPlan, User user) {
		//진행중인 일정 있을 경우 예외
		travelPlanUserService.validateMakeTravelPlan(user);
		//TravelPlan 생성 및 저장
		travelPlanRepository.save(travelPlan);
		//TravelPlan 및 User 저장.
		travelPlanUserService.saveTravelPlanWithUser(travelPlan, user);
		//daySchedule 생성 및 저장.
		dayScheduleService.initTravelPlan(travelPlan, createDateList(travelPlan.getStartDate(),
			travelPlan.getTravelDays()));
		return new TravelPlanResponseModel(travelPlan);
	}

	@Transactional(readOnly = true)
	public DayScheduleResponseDto readScheduleByDay(TravelPlanAccessOption accessOption, int day) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption);
		LocalDateDao startDate = travelPlanRepository.findLocalDateById(
			accessOption.readTravelPlanUUID());
		LocalDate date = startDate.getLocalDate().plusDays(day - 1);

		return dayScheduleService.readDaySchedule(accessOption.readTravelPlanUUID(), date);
	}

	@Transactional
	public DayScheduleAddPlaceResponseDto addPlaceInDaySchedule(DayScheduleAccessOption accessOption, String placeUUID,
		String memo) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption.readTravelPlanAccessOption());
		//장소 불러오기
		Place place = placeService.readPlaceByUUID(UUID.fromString(placeUUID));
		//저장하기
		UUID dayScheduleUUID = dayScheduleService.addPlace(accessOption.readDayScheduleUUID(), place, memo);

		return new DayScheduleAddPlaceResponseDto(dayScheduleUUID);
	}

	@Transactional
	public void exchangePlaceSequenceInDaySchedule(DayScheduleAccessOption accessOption,
		List<UUID> placeUUIDs) {
		//접근 가능한 유저인지 확인
		travelPlanUserService.validateAccessTravelPlan(accessOption.readTravelPlanAccessOption());
		//하루 일정 장소 순서 exchange
		dayScheduleService.exchangePlaceSequence(accessOption.readDayScheduleUUID(), placeUUIDs);
	}

	private List<LocalDate> createDateList(LocalDate startTravelDate, int travelDays) {
		List<LocalDate> dates = new ArrayList<>();
		for (int i = 0; i < travelDays; i++) {
			LocalDate localDate = startTravelDate.plusDays(i);
			dates.add(localDate);
		}
		return dates;
	}
}
