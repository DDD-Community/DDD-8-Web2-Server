package ddd.caffeine.ratrip.module.place.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.Optional;

import org.springframework.stereotype.Component;

import ddd.caffeine.ratrip.common.exception.domain.BookmarkException;
import ddd.caffeine.ratrip.module.place.domain.Bookmark;

@Component
public class BookmarkValidator {
	public void validateExistBookmark(Bookmark bookmark) {
		if (bookmark == null) {
			throw new BookmarkException(NOT_FOUND_BOOKMARK_EXCEPTION);
		}
	}

	public void validateExistOptionalBookmark(Optional<Bookmark> bookmark) {
		bookmark.orElseThrow(() -> new BookmarkException(NOT_FOUND_BOOKMARK_EXCEPTION));
	}
}
