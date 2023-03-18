package ddd.caffeine.ratrip.module.travel_plan.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.travel_plan.domain.QTravelPlanUser.*;
import static org.springframework.util.ObjectUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.common.util.QuerydslUtils;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanUser;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanUserQueryRepository;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelPlanUserQueryRepositoryImpl implements TravelPlanUserQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public boolean existByUserAndTravelPlanUUID(User user, UUID travelPlanUUID) {
		return jpaQueryFactory
			.select(travelPlanUser.id)
			.from(travelPlanUser)
			.where(
				travelPlanUser.user.eq(user),
				travelPlanUser.travelPlan.id.eq(travelPlanUUID)
			)
			.fetchFirst() != null;
	}

	public TravelPlanUser findByUserLatestTravel(User user) {
		TravelPlanUser response = jpaQueryFactory
			.selectFrom(travelPlanUser)
			.where(
				travelPlanUser.user.eq(user), travelPlanUser.travelPlan.isDeleted.isFalse()
			)
			.orderBy(travelPlanUser.createdAt.desc())
			.fetchFirst();

		return response;
	}

	public TravelPlanUser findByUserUnFinishedTravel(User user) {
		TravelPlanUser response = jpaQueryFactory
			.selectFrom(travelPlanUser)
			.where(
				travelPlanUser.user.eq(user),
				travelPlanUser.travelPlan.isEnd.isFalse()
			)
			.orderBy(travelPlanUser.createdAt.desc())
			.fetchFirst();

		return response;
	}

	@Override
	public Slice<TravelPlanUser> findByUserPagination(User user, Pageable pageable) {
		List<TravelPlanUser> contents = jpaQueryFactory
			.selectFrom(travelPlanUser)
			.where(travelPlanUser.user.eq(user), travelPlanUser.travelPlan.isDeleted.isFalse())
			.orderBy(readOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return QuerydslUtils.toSlice(contents, pageable);
	}

	@Override
	public List<TravelPlanUser> findByUser(User user) {
		return jpaQueryFactory
			.selectFrom(travelPlanUser)
			.where(travelPlanUser.user.eq(user))
			.fetch();
	}

	@Override
	public Region findOngoingTravelPlanUserRegionByUser(User user) {
		return jpaQueryFactory
			.select(travelPlanUser.travelPlan.region)
			.from(travelPlanUser)
			.where(
				travelPlanUser.user.eq(user),
				travelPlanUser.travelPlan.isEnd.isFalse()
			)
			.fetchFirst();
	}

	private List<OrderSpecifier> readOrderSpecifiers(Pageable pageable) {
		List<OrderSpecifier> orders = new ArrayList<>();

		if (!isEmpty(pageable.getSort())) {
			for (Sort.Order order : pageable.getSort()) {
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
				switch (order.getProperty()) {
					case "createdAt":
						OrderSpecifier<?> createdAt = QuerydslUtils
							.getSortedColumn(direction, travelPlanUser, "createdAt");
						orders.add(createdAt);
						break;
					default:
						break;
				}
			}
		}
		return orders;
	}

}
