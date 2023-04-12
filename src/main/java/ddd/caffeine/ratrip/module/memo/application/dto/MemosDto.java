package ddd.caffeine.ratrip.module.memo.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemosDto {
	private final Long dayPlanId;

	public static MemosDto of(Long dayPlanId) {
		return new MemosDto(dayPlanId);
	}
}
