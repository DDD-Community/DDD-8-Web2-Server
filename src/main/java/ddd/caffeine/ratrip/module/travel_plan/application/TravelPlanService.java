package ddd.caffeine.ratrip.module.travel_plan.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.TravelPlanException;
import ddd.caffeine.ratrip.module.day_plan.application.DayPlanService;
import ddd.caffeine.ratrip.module.travel_plan.application.dto.CreateTravelPlanDto;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanRepository;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.CreateTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelPlanService {
	private final TravelPlanRepository travelPlanRepository;
	private final DayPlanService dayPlanService;

	public CreateTravelPlanResponseDto createTravelPlan(User user, CreateTravelPlanDto request) {
		validateExistOngoingTravelPlan(user);

		//TravelPlan 생성 및 저장
		TravelPlan travelPlan = travelPlanRepository.save(request.toEntity(user));

		//daySchedule 생성 및 저장.
		dayPlanService.createDayPlan(travelPlan, request.getTravelStartDate(), request.getTravelDays());

		return CreateTravelPlanResponseDto.of(travelPlan);
	}

	private void validateExistOngoingTravelPlan(User user) {
		travelPlanRepository.findByUser(user).ifPresent(travelPlan -> {
			throw new TravelPlanException(ALREADY_EXIST_TRAVEL_PLAN_EXCEPTION);
		});
	}
}
