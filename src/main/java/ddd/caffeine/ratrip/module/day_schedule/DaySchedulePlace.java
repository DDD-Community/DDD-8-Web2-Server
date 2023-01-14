package ddd.caffeine.ratrip.module.day_schedule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.common.jpa.AuditingTimeEntity;
import ddd.caffeine.ratrip.module.place.model.Place;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column
	private int order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "day_schedule_id", columnDefinition = "BINARY(16)")
	private DaySchedule daySchedule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id", columnDefinition = "BINARY(16)")
	private Place place;

	@Builder(access = AccessLevel.PACKAGE)
	public DaySchedulePlace(int order, DaySchedule daySchedule, Place place) {
		this.order = order;
		this.daySchedule = daySchedule;
		this.place = place;
	}
}
