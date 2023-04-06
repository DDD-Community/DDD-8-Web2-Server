package ddd.caffeine.ratrip.module.memo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.travel_plan.place.domain.Place;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column
	private int sequence;

	@Column(columnDefinition = "VARCHAR(255)")
	private String content;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
	private boolean isDeleted = Boolean.FALSE;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "day_plan_id")
	private DayPlan dayPlan;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
	private User user;

	@Builder
	private Memo(int sequence, String content, boolean isDeleted, Place place, DayPlan dayPlan, User user) {
		this.sequence = sequence;
		this.content = content;
		this.isDeleted = isDeleted;
		this.place = place;
		this.dayPlan = dayPlan;
		this.user = user;
	}

	public static Memo of(DayPlan dayPlan, int sequence, String content, Place place, User user) {
		return Memo.builder()
			.dayPlan(dayPlan)
			.sequence(sequence)
			.content(content)
			.isDeleted(false)
			.place(place)
			.user(user)
			.build();
	}

	public void changeSequence(int sequence) {
		this.sequence = sequence;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
