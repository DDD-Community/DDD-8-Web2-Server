package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.TerminatedTravelPlanDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TerminatedTravelPlansResponseDto {
	private List<TerminatedTravelPlanDao> terminatedTravelPlans;
	private boolean hasNext;

	public static TerminatedTravelPlansResponseDto of(List<TerminatedTravelPlanDao> terminatedTravelPlans,
		boolean hasNext) {
		return new TerminatedTravelPlansResponseDto(terminatedTravelPlans, hasNext);
	}
}
