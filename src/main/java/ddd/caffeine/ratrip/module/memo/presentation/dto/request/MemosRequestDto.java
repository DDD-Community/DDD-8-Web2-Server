package ddd.caffeine.ratrip.module.memo.presentation.dto.request;

import javax.validation.constraints.NotEmpty;

import ddd.caffeine.ratrip.module.memo.application.dto.MemosDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemosRequestDto {
	@NotEmpty
	private Long dayPlanId;

	public MemosDto toServiceDto() {
		return MemosDto.of(dayPlanId);
	}
}
