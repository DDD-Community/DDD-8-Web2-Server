package ddd.caffeine.ratrip.module.day_plan.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.DayPlanException;
import ddd.caffeine.ratrip.module.day_plan.application.dto.DayPlansDto;
import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.day_plan.domain.repository.DayPlanRepository;
import ddd.caffeine.ratrip.module.day_plan.presentation.dto.response.DayPlansResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DayPlanService {
	private final DayPlanRepository dayPlanRepository;

	public void createDayPlan(TravelPlan travelPlan, User user, LocalDate startTravelDate, int travelDays) {
		List<LocalDate> travelDates = createDateList(startTravelDate, travelDays);

		for (int i = 0; i < travelDays; i++) {
			dayPlanRepository.save(DayPlan.of(travelPlan, user, travelDates.get(i)));
		}
	}

	public DayPlansResponseDto getDayPlans(User user, DayPlansDto request) {
		List<DayPlan> dayPlans = dayPlanRepository.findByTravelPlanIdAndUser(request.getTravelPlanId(), user);
		return DayPlansResponseDto.of(dayPlans);
	}

	public DayPlan validateExistDayPlan(User user, Long dayPlanId) {
		return dayPlanRepository.findByIdAndUser(dayPlanId, user)
			.orElseThrow(() -> new DayPlanException(NOT_FOUND_DAY_PLAN_EXCEPTION));
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
