package ddd.caffeine.ratrip.module.travel_plan.place.domain.repository.implementation;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.travel_plan.place.domain.repository.PlaceQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceQueryRepositoryImpl implements PlaceQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
}
