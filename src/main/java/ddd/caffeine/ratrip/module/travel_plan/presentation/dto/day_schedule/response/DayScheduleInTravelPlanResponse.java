package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.response;

import java.time.LocalDate;
import java.util.UUID;

import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedule;
import lombok.Getter;

@Getter
public class DayScheduleInTravelPlanResponse {
	private UUID id;
	private LocalDate localDate;

	public DayScheduleInTravelPlanResponse(DaySchedule daySchedule) {
		this.id = daySchedule.readPrimaryKey();
		this.localDate = daySchedule.getDate();
	}
}
