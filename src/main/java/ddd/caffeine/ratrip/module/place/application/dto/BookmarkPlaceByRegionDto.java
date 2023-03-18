package ddd.caffeine.ratrip.module.place.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BookmarkPlaceByRegionDto {
	private final double latitude;
	private final double longitude;

	public static BookmarkPlaceByRegionDto of(double latitude, double longitude) {
		return new BookmarkPlaceByRegionDto(latitude, longitude);
	}
}
