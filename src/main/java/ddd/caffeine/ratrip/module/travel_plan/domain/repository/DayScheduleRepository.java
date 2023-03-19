package ddd.caffeine.ratrip.module.travel_plan.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedule;

@Repository
public interface DayScheduleRepository extends JpaRepository<DaySchedule, UUID>, DayScheduleQueryRepository {
	Optional<DaySchedule> findByIdAndTravelPlanId(UUID id, UUID travelPlanId);
}
