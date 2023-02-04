package ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.implementation;

import static ddd.caffeine.ratrip.module.place.domain.QPlace.*;
import static ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.QDaySchedulePlace.*;

import java.util.List;
import java.util.UUID;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedule;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedulePlace;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.DaySchedulePlaceQueryRepository;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.dao.DaySchedulePlaceDao;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.dao.QDaySchedulePlaceDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DaySchedulePlaceQueryRepositoryImpl implements DaySchedulePlaceQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	/**
	 * todo : 추후 삭제
	 */
	@Override
	public List<DaySchedulePlace> findByDayScheduleUUID(UUID dayScheduleUUID) {
		return jpaQueryFactory
			.selectFrom(daySchedulePlace)
			.where(daySchedulePlace.daySchedule.id.eq(dayScheduleUUID))
			.fetch();
	}

	@Override
	public List<DaySchedulePlaceDao> findDaySchedulePlaceDaoByDayScheduleUUIDAndPlaceUUID(UUID dayScheduleUUID,
		String placeUUID) {

		return jpaQueryFactory
			.select(new QDaySchedulePlaceDao(daySchedulePlace.id, daySchedulePlace.memo, daySchedulePlace.sequence,
				place.id, place.name, place.category, place.location
			))
			.from(daySchedulePlace)
			.innerJoin(daySchedulePlace.place, place)
			.where(
				daySchedulePlace.daySchedule.id.eq(dayScheduleUUID),
				placeUUIDEq(placeUUID)
			)
			.orderBy(daySchedulePlace.sequence.asc())
			.fetch();
	}

	@Override
	public List<DaySchedulePlace> findDaySchedulePlacesByDayScheduleUUID(UUID dayScheduleUUID) {
		return jpaQueryFactory
			.selectFrom(daySchedulePlace)
			.where(
				daySchedulePlace.daySchedule.id.eq(dayScheduleUUID)
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

	@Override
	public boolean existByUUID(UUID daySchedulePlaceUUID) {
		return jpaQueryFactory
			.selectFrom(daySchedulePlace)
			.where(
				daySchedulePlace.id.eq(daySchedulePlaceUUID)
			)
			.fetchFirst() != null;
	}

	@Override
	public String findRepresentativeImageLink(UUID dayScheduleUUID) {
		return jpaQueryFactory
			.select(daySchedulePlace.place.imageLink)
			.from(daySchedulePlace)
			.innerJoin(daySchedulePlace.place, place)
			.where(
				daySchedulePlace.daySchedule.id.eq(dayScheduleUUID),
				daySchedulePlace.place.imageLink.isNotNull()
			)
			.fetchFirst();
	}

	@Override
	public Long delete(UUID daySchedulePlaceUUID) {
		return jpaQueryFactory
			.delete(daySchedulePlace)
			.where(daySchedulePlace.id.eq(daySchedulePlaceUUID))
			.execute();
	}

	private BooleanExpression placeUUIDEq(String placeUUID) {
		return placeUUID == null ? null : daySchedulePlace.place.id.eq(UUID.fromString(placeUUID));
	}

}
