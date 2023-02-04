package ddd.caffeine.ratrip.module.place.application.dto;

import ddd.caffeine.ratrip.module.place.domain.sub_domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryPlaceByCoordinateDto {
	private final Category category;
	private final double latitude;
	private final double longitude;

	public static CategoryPlaceByCoordinateDto of(Category category, double latitude, double longitude) {
		return new CategoryPlaceByCoordinateDto(category, latitude, longitude);
	}
}
