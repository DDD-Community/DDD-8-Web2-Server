package ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.common.jpa.AuditingTimeEntity;
import ddd.caffeine.ratrip.common.util.SequentialUUIDGenerator;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DaySchedule extends AuditingTimeEntity {
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@NotNull
	@Column(columnDefinition = "DATE")
	private LocalDate date;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
	private boolean isDeleted = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "travel_plan_id", columnDefinition = "BINARY(16)")
	private TravelPlan travelPlan;

	@PrePersist
	public void createPrimaryKey() {
		//sequential uuid 생성
		this.id = SequentialUUIDGenerator.generate();
	}

	@Builder
	public DaySchedule(LocalDate date, TravelPlan travelPlan) {
		this.date = date;
		this.travelPlan = travelPlan;
	}

	public UUID readPrimaryKey() {
		return this.id;
	}

	public void delete() {
		this.isDeleted = Boolean.TRUE;
	}
}
