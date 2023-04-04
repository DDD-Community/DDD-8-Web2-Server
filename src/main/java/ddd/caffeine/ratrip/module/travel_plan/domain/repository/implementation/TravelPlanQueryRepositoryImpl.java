package ddd.caffeine.ratrip.module.travel_plan.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.travel_plan.domain.QTravelPlan.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.common.util.QuerydslUtils;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanQueryRepository;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.QTerminatedTravelPlanDao;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.TerminatedTravelPlanDao;
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

	@Override
	public Slice<TerminatedTravelPlanDao> findTerminatedTravelPlansByUser(User user, Pageable pageable) {
		List<TerminatedTravelPlanDao> contents = jpaQueryFactory
			.select(new QTerminatedTravelPlanDao(travelPlan.id, travelPlan.title, travelPlan.startDate))
			.from(travelPlan)
			.where(
				travelPlan.user.eq(user),
				travelPlan.isEnd.isTrue()
			)
			.orderBy(travelPlan.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return QuerydslUtils.toSlice(contents, pageable);
	}
}
