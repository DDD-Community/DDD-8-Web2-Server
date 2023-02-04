package ddd.caffeine.ratrip.module.place.application.dto;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Category;
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
