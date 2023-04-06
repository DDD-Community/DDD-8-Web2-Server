package ddd.caffeine.ratrip.module.bookmark.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendByBookmarkAndLocationDto {
	private final Double longitude;
	private final Double latitude;

	public static RecommendByBookmarkAndLocationDto of(Double longitude, Double latitude) {
		return new RecommendByBookmarkAndLocationDto(longitude, latitude);
	}
}
