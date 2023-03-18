package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import lombok.Getter;

@Getter
public class BookmarkResponseDto {
	private boolean isActivated;
	private boolean isPresent;

	public BookmarkResponseDto(boolean isActivated) {
		this.isActivated = isActivated;
		this.isPresent = Boolean.TRUE;
	}

	public BookmarkResponseDto() {
		this.isPresent = Boolean.FALSE;
	}

	public static BookmarkResponseDto hasBookmarkFalse() {
		return new BookmarkResponseDto();
	}
}
