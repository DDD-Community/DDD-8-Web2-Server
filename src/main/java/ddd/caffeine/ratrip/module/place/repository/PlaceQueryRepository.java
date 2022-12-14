package ddd.caffeine.ratrip.module.place.repository;

import java.util.List;

import ddd.caffeine.ratrip.module.place.model.Place;
import ddd.caffeine.ratrip.module.place.model.Region;

public interface PlaceQueryRepository {
	List<Place> findPopularPlacesInRegions(List<Region> regions, Integer limit);

}
