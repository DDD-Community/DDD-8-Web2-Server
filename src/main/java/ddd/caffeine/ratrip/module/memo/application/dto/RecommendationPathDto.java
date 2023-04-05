package ddd.caffeine.ratrip.module.memo.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendationPathDto {
	private final Long dayPlanId;
	private final Long memoId;

	public static RecommendationPathDto of(Long dayPlanId, Long memoId) {
		return new RecommendationPathDto(dayPlanId, memoId);
	}
}
