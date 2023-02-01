package ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedule;

@Repository
public interface DayScheduleRepository extends JpaRepository<DaySchedule, UUID>, DayScheduleQueryRepository {
}
