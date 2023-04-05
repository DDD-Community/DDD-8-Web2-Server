package ddd.caffeine.ratrip.module.memo.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.place.domain.Address;
import ddd.caffeine.ratrip.module.place.domain.Category;
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

	@NotNull
	@Column(columnDefinition = "VARCHAR(100)")
	private String name;

	@NotNull
	@Embedded
	private Address address;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(columnDefinition = "VARCHAR(255)")
	private String memo;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
	private boolean isDeleted = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "day_plan_id")
	private DayPlan dayPlan;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
	private User user;

	@Builder
	private Memo(int sequence, String memo, boolean isDeleted, DayPlan dayPlan, Address address, Category category,
		String name, User user) {
		this.sequence = sequence;
		this.memo = memo;
		this.isDeleted = isDeleted;
		this.dayPlan = dayPlan;
		this.address = address;
		this.category = category;
		this.name = name;
		this.user = user;
	}

	public static Memo of(DayPlan dayPlan, int sequence, String memo, Address address, Category category, String name,
		User user) {
		return Memo.builder()
			.dayPlan(dayPlan)
			.sequence(sequence)
			.memo(memo)
			.isDeleted(false)
			.address(address)
			.category(category)
			.name(name)
			.user(user)
			.build();
	}
}
