package ddd.caffeine.ratrip.module.place.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.RecommendByLocationDao;

public interface PlaceQueryRepository {
	Slice<RecommendByLocationDao> findByRegion(Region region, Pageable pageable);
}
