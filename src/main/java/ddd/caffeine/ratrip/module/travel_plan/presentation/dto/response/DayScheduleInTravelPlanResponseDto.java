package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.day_schedule.domain.DaySchedule;
import lombok.Getter;

@Getter
public class DayScheduleInTravelPlanResponseDto {
	private List<DayScheduleInTravelPlanResponse> daySchedules;

	public DayScheduleInTravelPlanResponseDto(List<DaySchedule> daySchedules) {
		mapByDaySchedules(daySchedules);
	}

	public void mapByDaySchedules(List<DaySchedule> daySchedules) {
		this.daySchedules = new ArrayList<>();
		for (DaySchedule daySchedule : daySchedules) {
			this.daySchedules.add(new DayScheduleInTravelPlanResponse(daySchedule));
		}
	}
}
