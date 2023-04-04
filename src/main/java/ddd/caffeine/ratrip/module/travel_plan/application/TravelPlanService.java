package ddd.caffeine.ratrip.module.travel_plan.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.TravelPlanException;
import ddd.caffeine.ratrip.module.day_plan.application.DayPlanService;
import ddd.caffeine.ratrip.module.travel_plan.application.dto.CreateTravelPlanDto;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanRepository;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.TerminatedTravelPlanDao;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.CreateTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.TerminatedTravelPlansResponseDto;
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

	@Transactional(readOnly = true)
	public void endTravelPlan(User user, Long travelPlanId) {
		TravelPlan travelPlan = validateExistTravelPlan(user, travelPlanId);
		travelPlan.endTravelPlan();
	}

	@Transactional(readOnly = true)
	public TerminatedTravelPlansResponseDto getTerminatedTravelPlans(User user, Pageable pageable) {
		Slice<TerminatedTravelPlanDao> terminatedTravelPlans = travelPlanRepository.findTerminatedTravelPlansByUser(
			user, pageable);
		
		return TerminatedTravelPlansResponseDto.of(terminatedTravelPlans.getContent(), terminatedTravelPlans.hasNext());

	}

	private TravelPlan validateExistTravelPlan(User user, Long travelPlanId) {
		return travelPlanRepository.findByIdAndUser(travelPlanId, user)
			.orElseThrow(() -> new TravelPlanException(NOT_FOUND_TRAVEL_PLAN_EXCEPTION));
	}

	private void validateExistOngoingTravelPlan(User user) {
		travelPlanRepository.findOngoingTravelPlanByUser(user)
			.ifPresent(plan -> {
				throw new TravelPlanException(ALREADY_EXIST_TRAVEL_PLAN_EXCEPTION);
			});
	}
}
