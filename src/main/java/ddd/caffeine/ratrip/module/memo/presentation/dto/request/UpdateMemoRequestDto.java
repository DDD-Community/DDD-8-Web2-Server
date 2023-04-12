package ddd.caffeine.ratrip.module.memo.presentation.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.memo.application.dto.UpdateMemoDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemoRequestDto {
	@NotNull(message = "DayPlanId must not be null")
	private Long dayPlanId;

	@NotBlank(message = "Content must not be blank")
	private String content;

	public UpdateMemoDto toServiceDto(Long memoId) {
		return UpdateMemoDto.of(dayPlanId, content, memoId);
	}
}
