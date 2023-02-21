package ddd.caffeine.ratrip.module.place.domain.bookmark;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkId implements Serializable {
	private UUID user;
	private UUID place;

	public BookmarkId(UUID userUUID, UUID placeUUID) {
		this.user = userUUID;
		this.place = placeUUID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BookmarkId bookmarkId = (BookmarkId)o;
		return Objects.equals(this.user, bookmarkId.user) &&
			Objects.equals(this.place, bookmarkId.place);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.user, this.place);
	}
}
