package ddd.caffeine.ratrip.module.place.domain.bookmark;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(BookmarkId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends AuditingTimeEntity {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
	private User user;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id", columnDefinition = "BINARY(16)", nullable = false)
	private Place place;

	@NotNull
	@Column(name = "is_activated", columnDefinition = "TINYINT(1)")
	private boolean isActivated;

	@Builder
	private Bookmark(User user, Place place) {
		this.user = user;
		this.place = place;
		this.isActivated = Boolean.TRUE;
	}

	public static Bookmark of(User user, Place place) {
		return Bookmark.builder()
			.user(user)
			.place(place)
			.build();
	}

	public void changeBookmarkState() {
		if (this.isActivated) {
			this.isActivated = Boolean.FALSE;
			return;
		}
		this.isActivated = Boolean.TRUE;
	}
}
