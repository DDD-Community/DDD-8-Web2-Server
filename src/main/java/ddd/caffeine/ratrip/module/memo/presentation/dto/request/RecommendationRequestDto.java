package ddd.caffeine.ratrip.module.memo.presentation.dto.request;

import javax.validation.constraints.NotBlank;

import ddd.caffeine.ratrip.module.memo.application.dto.RecommendationPathDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendationRequestDto {
	@NotBlank(message = "DayPlanId must not be empty")
	private Long dayPlanId;

	public RecommendationPathDto toServiceDto(Long placeId) {
		return RecommendationPathDto.of(dayPlanId, placeId);
	}
}
