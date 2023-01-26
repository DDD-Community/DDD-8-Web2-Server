package ddd.caffeine.ratrip.module.place.presentation.dto.bookmark;

import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.Bookmark;
import lombok.Getter;

@Getter
public class BookmarkResponseDto {
	private UUID id;
	private boolean isBookmarked;

	public BookmarkResponseDto(Bookmark bookmark) {
		this.id = bookmark.getId();
		this.isBookmarked = bookmark.isActivated();
	}
}
