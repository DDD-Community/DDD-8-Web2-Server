package ddd.caffeine.ratrip.module.memo.presentation.dto.request;

import javax.validation.constraints.NotEmpty;

import ddd.caffeine.ratrip.module.memo.application.dto.CreateMemoDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemoRequestDto {

	@NotEmpty(message = "DayPlanId must not be empty")
	private Long dayPlanId;

	@NotEmpty(message = "KakaoId must not be empty")
	private String kakaoId;

	@NotEmpty(message = "sequence must not be empty")
	private int sequence;

	private String content;

	public CreateMemoDto toServiceDto() {
		return CreateMemoDto.of(dayPlanId, kakaoId, sequence, content);
	}
}
