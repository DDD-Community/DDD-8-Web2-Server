package ddd.caffeine.ratrip.module.day_plan.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.day_plan.domain.repository.DayPlanRepository;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DayPlanService {
	private final DayPlanRepository dayPlanRepository;

	public void createDayPlan(TravelPlan travelPlan, LocalDate startTravelDate, int travelDays) {
		List<LocalDate> travelDates = createDateList(startTravelDate, travelDays);

		for (int i = 0; i < travelDays; i++) {
			dayPlanRepository.save(DayPlan.of(travelPlan, travelDates.get(i)));
		}
	}

	private List<LocalDate> createDateList(LocalDate startTravelDate, int travelDays) {
		List<LocalDate> dates = new ArrayList<>();

		for (int i = 0; i < travelDays; i++) {
			LocalDate localDate = startTravelDate.plusDays(i);
			dates.add(localDate);
		}

		return dates;
	}
}
