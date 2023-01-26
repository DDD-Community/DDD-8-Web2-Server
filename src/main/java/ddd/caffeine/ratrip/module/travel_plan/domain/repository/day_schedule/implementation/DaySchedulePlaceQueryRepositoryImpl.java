package ddd.caffeine.ratrip.module.travel_plan.domain.repository.day_schedule.implementation;

import static ddd.caffeine.ratrip.module.place.domain.QPlace.*;
import static ddd.caffeine.ratrip.module.travel_plan.domain.QDaySchedulePlace.*;

import java.util.List;
import java.util.UUID;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedule;
import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedulePlace;
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
				daySchedulePlace.memo, daySchedulePlace.sequence))
			.from(daySchedulePlace)
			.innerJoin(daySchedulePlace.place, place)
			.where(
				daySchedulePlace.daySchedule.id.eq(dayScheduleUUID)
			)
			.orderBy(daySchedulePlace.sequence.asc())
			.fetch();
	}

	@Override
	public List<DaySchedulePlace> findByDayScheduleUUIDAndPlaceUUIDs(UUID dayScheduleUUID, UUID firstPlaceUUID,
		UUID secondPlaceUUID) {
		return jpaQueryFactory
			.selectFrom(daySchedulePlace)
			.where(
				daySchedulePlace.daySchedule.id.eq(dayScheduleUUID),
				daySchedulePlace.place.id.eq(firstPlaceUUID)
					.or(daySchedulePlace.place.id.eq(secondPlaceUUID))
			)
			.fetch();
	}

	@Override
	public Integer countPlacesByDayScheduleUUID(UUID dayScheduleUUID) {
		return Math.toIntExact(jpaQueryFactory
			.select(daySchedulePlace.count())
			.from(daySchedulePlace)
			.where(daySchedulePlace.daySchedule.id.eq(dayScheduleUUID))
			.fetchFirst());
	}

	@Override
	public boolean existByDayScheduleAndPlace(DaySchedule daySchedule, Place place) {
		return jpaQueryFactory.selectFrom(daySchedulePlace)
			.where(
				daySchedulePlace.daySchedule.eq(daySchedule),
				daySchedulePlace.place.eq(place)
			)
			.fetchFirst() != null;
	}
}
