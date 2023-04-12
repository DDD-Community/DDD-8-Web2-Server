package ddd.caffeine.ratrip.module.memo.application.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChangeMemoSequenceDto {
	private Long dayPlanId;
	private List<Long> memoIds;

	public static ChangeMemoSequenceDto of(Long dayPlanId, List<Long> memoIds) {
		return new ChangeMemoSequenceDto(dayPlanId, memoIds);
	}
}
