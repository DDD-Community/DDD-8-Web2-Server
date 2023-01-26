package ddd.caffeine.ratrip.module.place.domain;

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
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends AuditingTimeEntity {
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id", columnDefinition = "BINARY(16)", nullable = false)
	private Place place;

	@NotNull
	@Column(name = "is_activated", columnDefinition = "TINYINT(1)")
	private boolean isActivated = Boolean.TRUE;

	@PrePersist
	public void createBookmarkPrimaryKey() {
		//sequential uuid 생성
		this.id = SequentialUUIDGenerator.generate();
	}

	@Builder
	private Bookmark(User user, Place place) {
		this.user = user;
		this.place = place;
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
