package ddd.caffeine.ratrip.module.day_schedule_place.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.day_schedule_place.domain.DaySchedulePlace;

@Repository
public interface DaySchedulePlaceRepository
	extends JpaRepository<DaySchedulePlace, UUID>, DaySchedulePlaceQueryRepository {
}
