package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import ddd.caffeine.ratrip.module.day_schedule.domain.DaySchedule;
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
