package ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao;

import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class TerminatedTravelPlanDao {
	private final Long id;
	private final String title;
	private final LocalDate startDate;

	@QueryProjection
	public TerminatedTravelPlanDao(Long id, String title, LocalDate startDate) {
		this.id = id;
		this.title = title;
		this.startDate = startDate;
	}
}
