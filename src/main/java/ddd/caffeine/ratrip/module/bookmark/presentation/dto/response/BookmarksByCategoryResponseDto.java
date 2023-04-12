package ddd.caffeine.ratrip.module.bookmark.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.BookmarkByCategoryDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookmarksByCategoryResponseDto {
	private List<BookmarkByCategoryDao> places;
	private boolean hasNext;

	public static BookmarksByCategoryResponseDto of(List<BookmarkByCategoryDao> bookmarks, boolean hasNext) {
		return new BookmarksByCategoryResponseDto(bookmarks, hasNext);
	}
}
