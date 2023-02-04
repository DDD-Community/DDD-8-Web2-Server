package ddd.caffeine.ratrip.module.place.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.CategoryPlaceByRegionDao;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Category;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface PlaceQueryRepository {
	Slice<Place> findPlacesInRegions(List<Region> regions, Pageable pageable);

	Place findByThirdPartyID(String thirdPartyID);

	Slice<CategoryPlaceByRegionDao> getCategoryPlacesByRegion(User user, Region region, Category category,
		Pageable pageable);
}
