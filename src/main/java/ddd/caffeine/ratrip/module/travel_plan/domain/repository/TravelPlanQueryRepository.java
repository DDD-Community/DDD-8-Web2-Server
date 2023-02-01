package ddd.caffeine.ratrip.module.travel_plan.domain.repository;

import java.util.List;

import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;

public interface TravelPlanQueryRepository {
	List<TravelPlan> findAllOngoingTravelPlan();
}
