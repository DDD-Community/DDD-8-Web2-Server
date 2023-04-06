package ddd.caffeine.ratrip.module.bookmark.presentation.dto.request;

import javax.validation.constraints.NotEmpty;

import ddd.caffeine.ratrip.module.bookmark.application.dto.RecommendByBookmarkAndLocationDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecommendByBookmarkAndLocationRequestDto {
	@NotEmpty(message = "Longitude must not be Blank")
	private Double longitude;

	@NotEmpty(message = "Latitude must not be Blank")
	private Double latitude;

	public RecommendByBookmarkAndLocationDto toServiceDto() {
		return RecommendByBookmarkAndLocationDto.of(longitude, latitude);
	}
}
