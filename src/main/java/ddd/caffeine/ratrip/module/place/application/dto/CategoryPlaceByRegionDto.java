package ddd.caffeine.ratrip.module.place.application.dto;

import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryPlaceByRegionDto {
	private final Region region;
	private final Category category;

	public static CategoryPlaceByRegionDto of(Region region, Category category) {
		return new CategoryPlaceByRegionDto(region, category);
	}
}
