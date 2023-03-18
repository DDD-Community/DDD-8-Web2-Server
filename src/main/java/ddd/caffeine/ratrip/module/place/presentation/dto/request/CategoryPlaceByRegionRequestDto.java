package ddd.caffeine.ratrip.module.place.presentation.dto.request;

import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.place.application.dto.CategoryPlaceByRegionDto;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Region;
import lombok.Getter;

@Getter
public class CategoryPlaceByRegionRequestDto {
	@NotNull(message = "Region must not be null")
	private final Region region;

	@NotNull(message = "Category must not be null")
	private final Category category;

	public CategoryPlaceByRegionRequestDto(Region region, Category category) {
		this.region = region;
		this.category = category;
	}

	public CategoryPlaceByRegionDto toServiceDto() {
		return CategoryPlaceByRegionDto.of(region, category);
	}
}
