package ddd.caffeine.ratrip.module.day_schedule_place.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.day_schedule_place.domain.QDaySchedulePlace.*;
import static ddd.caffeine.ratrip.module.place.domain.QPlace.*;

import java.util.List;
import java.util.UUID;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.day_schedule.domain.DaySchedule;
import ddd.caffeine.ratrip.module.day_schedule.domain.respository.dao.PlaceNameLongitudeLatitudeDao;
import ddd.caffeine.ratrip.module.day_schedule_place.domain.DaySchedulePlace;
import ddd.caffeine.ratrip.module.day_schedule_place.domain.repository.DaySchedulePlaceQueryRepository;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.DaySchedulePlaceDao;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.QDaySchedulePlaceDao;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.QPlaceNameLongitudeLatitudeDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DaySchedulePlaceQueryRepositoryImpl implements DaySchedulePlaceQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<PlaceNameLongitudeLatitudeDao> findPlacesNameLongitudeLatitudeById(UUID id) {
		return jpaQueryFactory.select(new QPlaceNameLongitudeLatitudeDao(place.id, place.name, place.location.longitude,
				place.location.latitude))
			.from(daySchedulePlace)
			.innerJoin(daySchedulePlace.place, place)
			.where(daySchedulePlace.daySchedule.id.eq(id))
			.fetch();
	}

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
				daySchedulePlace.daySchedule.id.eq(dayScheduleUUID), daySchedulePlace.isDeleted.isFalse(),
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
	public List<DaySchedulePlace> findByDaySchedulePlaceGreaterThanSequence(UUID dayScheduleUUID, int sequence) {
		return jpaQueryFactory
			.selectFrom(daySchedulePlace)
			.where(
				daySchedulePlace.daySchedule.id.eq(dayScheduleUUID),
				daySchedulePlace.sequence.gt(sequence)
			)
			.fetch();
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
