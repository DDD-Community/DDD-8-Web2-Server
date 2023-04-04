package ddd.caffeine.ratrip.module.travel_plan.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.travel_plan.domain.QTravelPlan.*;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanQueryRepository;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelPlanQueryRepositoryImpl implements TravelPlanQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<TravelPlan> findOngoingTravelPlanByUser(User user) {
		return Optional.ofNullable(jpaQueryFactory
			.selectFrom(travelPlan)
			.where(
				travelPlan.user.eq(user),
				travelPlan.isEnd.isFalse()
			)
			.fetchOne());
	}

	@Override
	public Optional<TravelPlan> findByIdAndUser(Long id, User user) {
		return Optional.ofNullable(jpaQueryFactory
			.selectFrom(travelPlan)
			.where(
				travelPlan.id.eq(id),
				travelPlan.user.eq(user),
				travelPlan.isEnd.isFalse()
			)
			.fetchOne()
		);
	}
}
