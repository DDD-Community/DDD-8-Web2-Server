package ddd.caffeine.ratrip.module.bookmark.domain;

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
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends AuditingTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
	private boolean isDeleted = Boolean.FALSE;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
	private User user;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

	@Builder
	private Bookmark(User user, Place place, boolean isDeleted) {
		this.user = user;
		this.place = place;
		this.isDeleted = isDeleted;
	}

	public static Bookmark of(User user, Place place) {
		return Bookmark.builder()
			.user(user)
			.place(place)
			.isDeleted(Boolean.FALSE)
			.build();
	}
}
