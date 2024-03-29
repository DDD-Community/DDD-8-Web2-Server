package ddd.caffeine.ratrip.module.day_plan.domain;

import java.time.LocalDate;

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
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DayPlan extends AuditingTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(columnDefinition = "DATE")
	private LocalDate date;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
	private boolean isDeleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "travel_plan_id")
	private TravelPlan travelPlan;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
	private User user;

	@Builder
	private DayPlan(LocalDate date, TravelPlan travelPlan, boolean isDeleted, User user) {
		this.date = date;
		this.travelPlan = travelPlan;
		this.isDeleted = isDeleted;
		this.user = user;
	}

	public static DayPlan of(TravelPlan travelPlan, User user, LocalDate date) {
		return DayPlan.builder()
			.travelPlan(travelPlan)
			.user(user)
			.date(date)
			.isDeleted(false)
			.build();
	}
}
