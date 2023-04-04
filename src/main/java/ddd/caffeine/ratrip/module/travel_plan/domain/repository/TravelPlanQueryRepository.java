package ddd.caffeine.ratrip.module.travel_plan.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.TerminatedTravelPlanDao;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface TravelPlanQueryRepository {
	Optional<TravelPlan> findOngoingTravelPlanByUser(User user);

	Optional<TravelPlan> findByIdAndUser(Long id, User user);

	Slice<TerminatedTravelPlanDao> findTerminatedTravelPlansByUser(User user, Pageable pageable);
}
