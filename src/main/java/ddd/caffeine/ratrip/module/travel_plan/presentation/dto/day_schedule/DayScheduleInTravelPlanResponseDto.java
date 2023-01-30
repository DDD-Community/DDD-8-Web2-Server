package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedule;
import lombok.Getter;

@Getter
public class DayScheduleInTravelPlanResponseDto {
	private List<DayScheduleInTravelPlanResponseModel> daySchedules;

	public DayScheduleInTravelPlanResponseDto(List<DaySchedule> daySchedules) {
		mapByDaySchedules(daySchedules);
	}

	public void mapByDaySchedules(List<DaySchedule> daySchedules) {
		this.daySchedules = new ArrayList<>();
		for (DaySchedule daySchedule : daySchedules) {
			this.daySchedules.add(new DayScheduleInTravelPlanResponseModel(daySchedule));
		}
	}
}
