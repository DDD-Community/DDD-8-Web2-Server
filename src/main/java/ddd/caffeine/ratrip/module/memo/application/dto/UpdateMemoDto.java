package ddd.caffeine.ratrip.module.memo.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UpdateMemoDto {
	private final Long dayPlanId;
	private final String content;
	private final Long memoId;

	public static UpdateMemoDto of(Long dayPlanId, String content, Long memoId) {
		return new UpdateMemoDto(dayPlanId, content, memoId);
	}
}
