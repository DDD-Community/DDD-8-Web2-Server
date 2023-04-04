package ddd.caffeine.ratrip.module.travel_plan.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.common.jpa.AuditingTimeEntity;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TravelPlan extends AuditingTimeEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column
	private String title;

	@NotNull
	@Column
	@Enumerated(EnumType.STRING)
	private Region region;

	@NotNull
	@Column
	private int travelDays;

	@NotNull
	@Column(columnDefinition = "DATE")
	private LocalDate startDate;

	@NotNull
	@Column(name = "is_end", columnDefinition = "TINYINT(1)")
	private boolean isEnd;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
	private boolean isDeleted;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
	private User user;

	@Builder
	private TravelPlan(String title, Region region, int travelDays, LocalDate startDate, User user) {
		this.title = title;
		this.region = region;
		this.travelDays = travelDays;
		this.startDate = startDate;
		this.isEnd = Boolean.FALSE;
		this.isDeleted = Boolean.FALSE;
		this.user = user;
	}

	public static TravelPlan of(String title, Region region, int travelDays, LocalDate startDate, User user) {
		return TravelPlan.builder()
			.title(title)
			.region(region)
			.travelDays(travelDays)
			.startDate(startDate)
			.user(user)
			.build();
	}
}
