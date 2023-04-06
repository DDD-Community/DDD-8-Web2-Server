package ddd.caffeine.ratrip.module.memo.presentation.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.memo.application.dto.CreateMemoDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemoRequestDto {

	@NotNull(message = "DayPlanId must not be empty")
	private Long dayPlanId;

	@NotEmpty(message = "KakaoId must not be empty")
	private String kakaoId;

	@NotNull(message = "sequence must not be empty")
	private Integer sequence;

	private String content;

	public CreateMemoDto toServiceDto() {
		return CreateMemoDto.of(dayPlanId, kakaoId, sequence, content);
	}
}
