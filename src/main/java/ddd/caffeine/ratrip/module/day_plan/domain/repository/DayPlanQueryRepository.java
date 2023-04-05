package ddd.caffeine.ratrip.module.day_plan.domain.repository;

import java.util.List;

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface DayPlanQueryRepository {
	List<DayPlan> findByTravelPlanIdAndUser(Long travelPlanId, User user);
}
