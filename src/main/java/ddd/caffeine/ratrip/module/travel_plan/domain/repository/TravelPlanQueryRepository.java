package ddd.caffeine.ratrip.module.travel_plan.domain.repository;

import java.util.Optional;

import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface TravelPlanQueryRepository {
	Optional<TravelPlan> findOngoingTravelPlanByUser(User user);

	Optional<TravelPlan> findByIdAndUser(Long id, User user);
}
