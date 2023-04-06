package ddd.caffeine.ratrip.module.place.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendByLocationDto {
	private double latitude;
	private double longitude;

	public static RecommendByLocationDto of(double latitude, double longitude) {
		return new RecommendByLocationDto(latitude, longitude);
	}
}
