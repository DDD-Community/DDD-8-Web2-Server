package ddd.caffeine.ratrip.module.day_plan.domain.repository.dao;

import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class DayPlanDao {
	private final Long id;
	private final LocalDate date;

	@QueryProjection
	public DayPlanDao(Long id, LocalDate date) {
		this.id = id;
		this.date = date;
	}
}
