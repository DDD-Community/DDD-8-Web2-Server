package ddd.caffeine.ratrip.module.bookmark.presentation.dto.request;

import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.bookmark.application.dto.RecommendByBookmarkAndLocationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendByBookmarkAndLocationRequestDto {
	@NotNull(message = "Longitude must not be null")
	private Double longitude;

	@NotNull(message = "Latitude must not be null")
	private Double latitude;

	public RecommendByBookmarkAndLocationDto toServiceDto() {
		return RecommendByBookmarkAndLocationDto.of(longitude, latitude);
	}
}
