package ddd.caffeine.ratrip.module.travel_plan.domain.repository.implementation;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelPlanQueryRepositoryImpl implements TravelPlanQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

}
