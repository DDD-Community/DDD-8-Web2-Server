package ddd.caffeine.ratrip.module.travel_plan.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long>, TravelPlanQueryRepository {
	Optional<TravelPlan> findByUser(User user);
}
