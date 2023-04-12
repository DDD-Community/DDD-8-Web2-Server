package ddd.caffeine.ratrip.module.place.presentation.dto.request;

import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.place.application.dto.RecommendByLocationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendByLocationRequestDto {
	@NotNull(message = "Latitude must not be null")
	private Double latitude;

	@NotNull(message = "Longitude must not be null")
	private Double longitude;

	public RecommendByLocationDto toServiceDto() {
		return RecommendByLocationDto.of(latitude, longitude);
	}
}
