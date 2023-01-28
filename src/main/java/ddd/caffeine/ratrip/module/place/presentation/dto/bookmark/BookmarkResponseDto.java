package ddd.caffeine.ratrip.module.place.presentation.dto.bookmark;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkResponseDto {
	private boolean isBookmarked;
	private boolean hasBookmark;

	@Builder
	public BookmarkResponseDto(boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
		this.hasBookmark = Boolean.TRUE;
	}

	public BookmarkResponseDto() {
		this.hasBookmark = Boolean.FALSE;
	}

	public static BookmarkResponseDto hasBookmarkedFalse() {
		return new BookmarkResponseDto();
	}
}
