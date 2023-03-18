package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.repository.dao.CategoryPlaceByRegionDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CategoryPlacesByRegionResponseDto {
	private List<CategoryPlaceByRegionDao> places;
	private boolean hasNext;

	public static CategoryPlacesByRegionResponseDto of(List<CategoryPlaceByRegionDao> places, boolean hasNext) {
		return new CategoryPlacesByRegionResponseDto(places, hasNext);
	}
}
