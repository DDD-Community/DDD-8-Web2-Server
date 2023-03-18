package ddd.caffeine.ratrip.module.travel_plan.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedule;

public interface DayScheduleQueryRepository {
	DaySchedule findByTravelPlanIdAndDate(UUID travelPlanId, LocalDate date);

	List<DaySchedule> findByTravelPlanId(UUID travelPlanId);
}
