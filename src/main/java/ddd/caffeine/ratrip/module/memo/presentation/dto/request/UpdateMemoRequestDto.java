package ddd.caffeine.ratrip.module.memo.presentation.dto.request;

import javax.validation.constraints.NotEmpty;

import ddd.caffeine.ratrip.module.memo.application.dto.UpdateMemoDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemoRequestDto {
	@NotEmpty(message = "DayPlanId must not be empty")
	private Long dayPlanId;

	@NotEmpty(message = "Content must not be empty")
	private String content;

	public UpdateMemoDto toServiceDto(Long memoId) {
		return UpdateMemoDto.of(dayPlanId, content, memoId);
	}
}
