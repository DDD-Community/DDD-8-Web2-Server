package ddd.caffeine.ratrip.module.day_plan.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;

@Repository
public interface DayPlanRepository extends JpaRepository<DayPlan, Long> {
}
