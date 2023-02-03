package ddd.caffeine.ratrip.module.place.presentation.dto.request;

import javax.validation.constraints.NotBlank;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.place.application.dto.CategoryPlaceByRegionDto;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Category;
import lombok.Getter;

@Getter
public class CategoryPlaceByRegionRequestDto {
	@NotBlank(message = "Region must not be blank")
	private final Region region;

	@NotBlank(message = "Category must not be blank")
	private final Category category;

	public CategoryPlaceByRegionRequestDto(Region region, Category category) {
		this.region = region;
		this.category = category;
	}

	public CategoryPlaceByRegionDto toServiceDto() {
		return CategoryPlaceByRegionDto.of(region, category);
	}
}