package ddd.caffeine.ratrip.module.place.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.place.domain.Place;

public interface PlaceQueryRepository {
	Slice<Place> findPlacesInRegions(List<Region> regions, Pageable pageable);

	Place findByThirdPartyID(String thirdPartyID);
}
