package ddd.caffeine.ratrip.module.place.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkPlaceByRegionDto {
	private final double latitude;
	private final double longitude;

	public static BookmarkPlaceByRegionDto of(double latitude, double longitude) {
		return new BookmarkPlaceByRegionDto(latitude, longitude);
	}
}
