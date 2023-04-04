package ddd.caffeine.ratrip.module.day_schedule.domain.respository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import ddd.caffeine.ratrip.module.day_schedule.domain.DaySchedule;

public interface DayScheduleQueryRepository {
	DaySchedule findByTravelPlanIdAndDate(UUID travelPlanId, LocalDate date);

	List<DaySchedule> findByTravelPlanId(UUID travelPlanId);
}
