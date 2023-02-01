package ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedulePlace;

@Repository
public interface DaySchedulePlaceRepository
	extends JpaRepository<DaySchedulePlace, UUID>, DaySchedulePlaceQueryRepository {
}
