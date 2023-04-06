package ddd.caffeine.ratrip.module.day_plan.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.day_plan.domain.repository.dao.DayPlanDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DayPlansResponseDto {
	List<DayPlanDao> dayPlans;

	public static DayPlansResponseDto of(List<DayPlanDao> dayPlans) {
		return new DayPlansResponseDto(dayPlans);
	}
}
