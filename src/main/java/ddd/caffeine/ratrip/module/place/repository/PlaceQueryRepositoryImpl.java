package ddd.caffeine.ratrip.module.place.repository;

import static ddd.caffeine.ratrip.module.place.model.QPlace.*;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.Region;
import ddd.caffeine.ratrip.module.place.model.Place;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceQueryRepositoryImpl implements PlaceQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public List<Place> findPopularPlacesInRegions(List<Region> regions, Integer limit) {
		return jpaQueryFactory
			.selectFrom(place)
			.where(regionsIn(regions))
			.orderBy(place.numberOfTrips.desc())
			.limit(limit)
			.fetch();
	}

	private BooleanExpression regionsIn(List<Region> region) {
		return region.isEmpty() ? null : place.address.region.in(region);
	}
}
