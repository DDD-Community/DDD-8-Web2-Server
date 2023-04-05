package ddd.caffeine.ratrip.module.day_plan.domain.repository.impl;

import static ddd.caffeine.ratrip.module.day_plan.domain.QDayPlan.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.day_plan.domain.repository.DayPlanQueryRepository;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DayPlanQueryRepositoryImpl implements DayPlanQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<DayPlan> findByTravelPlanIdAndUser(Long travelPlanId, User user) {
		return jpaQueryFactory
			.selectFrom(dayPlan)
			.where(
				dayPlan.travelPlan.id.eq(travelPlanId),
				dayPlan.travelPlan.user.eq(user)
			)
			.fetch();
	}
}
