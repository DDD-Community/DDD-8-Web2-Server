package ddd.caffeine.ratrip.module.travel_plan.domain.repository.day_schedule.implementation;

import static ddd.caffeine.ratrip.module.place.domain.QPlace.*;
import static ddd.caffeine.ratrip.module.travel_plan.domain.QDaySchedulePlace.*;

import java.util.List;
import java.util.UUID;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.DaySchedulePlaceDao;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.QDaySchedulePlaceDao;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.day_schedule.DaySchedulePlaceQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DaySchedulePlaceQueryRepositoryImpl implements DaySchedulePlaceQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<DaySchedulePlaceDao> findDaySchedulePlaceDaoByDayScheduleUUID(UUID dayScheduleUUID) {
		return jpaQueryFactory
			.select(new QDaySchedulePlaceDao(place.id, place.name, place.category,
				daySchedulePlace.memo, daySchedulePlace.order))
			.from(daySchedulePlace)
			.innerJoin(daySchedulePlace.place, place)
			.where(
				daySchedulePlace.daySchedule.id.eq(dayScheduleUUID)
			)
			.orderBy(daySchedulePlace.order.asc())
			.fetch();
	}
}
