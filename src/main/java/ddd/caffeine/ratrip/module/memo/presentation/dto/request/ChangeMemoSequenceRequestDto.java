package ddd.caffeine.ratrip.module.memo.presentation.dto.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.memo.application.dto.ChangeMemoSequenceDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangeMemoSequenceRequestDto {

	@NotNull(message = "DayPlanId must not be null")
	private Long dayPlanId;

	@NotEmpty(message = "MemoIds must not be empty")
	private List<Long> memoIds;

	public ChangeMemoSequenceDto toServiceDto() {
		return ChangeMemoSequenceDto.of(dayPlanId, memoIds);
	}
}
