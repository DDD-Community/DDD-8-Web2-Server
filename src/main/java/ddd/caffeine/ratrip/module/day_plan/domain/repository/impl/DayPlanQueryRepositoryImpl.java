package ddd.caffeine.ratrip.module.day_plan.domain.repository.impl;

import static ddd.caffeine.ratrip.module.day_plan.domain.QDayPlan.*;

import java.util.List;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.day_plan.domain.repository.DayPlanQueryRepository;
import ddd.caffeine.ratrip.module.day_plan.domain.repository.dao.DayPlanDao;
import ddd.caffeine.ratrip.module.day_plan.domain.repository.dao.QDayPlanDao;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DayPlanQueryRepositoryImpl implements DayPlanQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<DayPlanDao> findByTravelPlanIdAndUser(Long travelPlanId, User user) {
		return jpaQueryFactory.select(
				new QDayPlanDao(dayPlan.id, dayPlan.date))
			.from(dayPlan)
			.where(
				dayPlan.travelPlan.id.eq(travelPlanId),
				dayPlan.user.eq(user)
			)
			.fetch();
	}

	@Override
	public Optional<DayPlan> findByIdAndUser(Long dayPlanId, User user) {
		return Optional.ofNullable(
			jpaQueryFactory.selectFrom(dayPlan)
				.where(
					dayPlan.id.eq(dayPlanId),
					dayPlan.user.eq(user)
				)
				.fetchOne());
	}
}
