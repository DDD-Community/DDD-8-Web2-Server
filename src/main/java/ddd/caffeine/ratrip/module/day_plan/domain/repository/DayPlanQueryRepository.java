package ddd.caffeine.ratrip.module.day_plan.domain.repository;

import java.util.List;
import java.util.Optional;

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.day_plan.domain.repository.dao.DayPlanDao;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface DayPlanQueryRepository {
	List<DayPlanDao> findByTravelPlanIdAndUser(Long travelPlanId, User user);

	Optional<DayPlan> findByIdAndUser(Long dayPlanId, User user);
}
