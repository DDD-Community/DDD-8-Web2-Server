package ddd.caffeine.ratrip.module.travel_plan.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelPlanService {

	// public TravelPlanResponseDto createTravelPlan(TravelPlan travelPlan, User user) {
	// 	//진행중인 일정 있을 경우 예외
	// 	travelPlanUserService.validateMakeTravelPlan(user);
	// 	//TravelPlan 생성 및 저장
	// 	travelPlanRepository.save(travelPlan);
	// 	//TravelPlan 및 User 저장.
	// 	travelPlanUserService.saveTravelPlanWithUser(travelPlan, user);
	// 	//daySchedule 생성 및 저장.
	// 	dayScheduleService.initTravelPlan(travelPlan, createDateList(travelPlan.getStartDate(),
	// 		travelPlan.getTravelDays()));
	// 	return new TravelPlanResponseDto(travelPlan);
	// }
}
