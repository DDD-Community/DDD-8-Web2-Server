package ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule;

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
import ddd.caffeine.ratrip.module.place.domain.Place;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DaySchedule 과 Place 의 N:M 관계를 해소하기 위한 매핑 테이블 입니다.
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DaySchedulePlace extends AuditingTimeEntity {

	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@NotNull
	@Column
	private int sequence;

	/*
	영어, 한글 글자 모두 255 글자 가능 합니다.
	 */
	@Column(columnDefinition = "VARCHAR(255)")
	private String memo;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
	private boolean isDeleted = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "day_schedule_id", columnDefinition = "BINARY(16)")
	private DaySchedule daySchedule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id", columnDefinition = "BINARY(16)")
	private Place place;

	@PrePersist
	public void createDaySchedulePrimaryKey() {
		//sequential uuid 생성
		this.id = SequentialUUIDGenerator.generate();
	}

	@Builder
	public DaySchedulePlace(int sequence, String memo, DaySchedule daySchedule, Place place) {
		this.memo = memo;
		this.sequence = sequence;
		this.daySchedule = daySchedule;
		this.place = place;
	}

	public void changeSequence(int sequence) {
		this.sequence = sequence;
	}

	public void minusSequence() {
		this.sequence -= 1;
	}

	public void update(String memo) {
		this.memo = memo;
	}
}
