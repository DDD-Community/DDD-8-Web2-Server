package ddd.caffeine.ratrip.module.travel_plan.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long>, TravelPlanQueryRepository {
}
