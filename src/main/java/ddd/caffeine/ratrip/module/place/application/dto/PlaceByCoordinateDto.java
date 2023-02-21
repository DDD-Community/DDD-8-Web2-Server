package ddd.caffeine.ratrip.module.place.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlaceByCoordinateDto {
	private final double latitude;
	private final double longitude;

	public static PlaceByCoordinateDto of(double latitude, double longitude) {
		return new PlaceByCoordinateDto(latitude, longitude);
	}
}
