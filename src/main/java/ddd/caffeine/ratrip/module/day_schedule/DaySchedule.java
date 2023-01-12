package ddd.caffeine.ratrip.module.day_schedule;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ddd.caffeine.ratrip.module.travel_plan.TravelPlan;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class DaySchedule {
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@Column(columnDefinition = "DATE")
	private LocalDate date;

	//Todo : 타입 고민.
	@Column(columnDefinition = "VARCHAR(255)")
	private String memo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "travel_plan_id", columnDefinition = "BINARY(16)")
	private TravelPlan travelPlan;
}
