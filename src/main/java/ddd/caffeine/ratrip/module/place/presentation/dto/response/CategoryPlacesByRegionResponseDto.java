package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.repository.dao.CategoryPlaceByRegionDao;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryPlacesByRegionResponseDto {
	private List<CategoryPlaceByRegionDao> places;
	private boolean hasNext;
}
