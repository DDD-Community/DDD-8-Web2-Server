package ddd.caffeine.ratrip.module.travel_plan.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.travel_plan.domain.QTravelPlan.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelPlanQueryRepositoryImpl implements TravelPlanQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<TravelPlan> findAllOngoingTravelPlan() {
		return jpaQueryFactory
			.selectFrom(travelPlan)
			.where(travelPlan.isEnd.isFalse())
			.fetch();
	}
}
