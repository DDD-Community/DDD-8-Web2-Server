package ddd.caffeine.ratrip.module.place.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SearchPlaceDto {
	private String keyword;
	private double latitude;
	private double longitude;
	private int page;

	public static SearchPlaceDto of(String keyword, double latitude, double longitude, int page) {
		return new SearchPlaceDto(keyword, latitude, longitude, page);
	}
}
