package ddd.caffeine.ratrip.module.bookmark.application;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.module.bookmark.domain.Bookmark;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.BookmarkRepository;
import ddd.caffeine.ratrip.module.bookmark.presentation.dto.response.BookmarkPlaceDto;
import ddd.caffeine.ratrip.module.bookmark.presentation.dto.response.BookmarksResponseDto;
import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
	private final PlaceService placeService;
	private final BookmarkRepository bookmarkRepository;
	private final BookmarkValidator bookmarkValidator;

	public boolean isBookmarked(final UUID placeId, final UUID userId) {
		return bookmarkRepository.findByUserIdAndPlaceId(userId, placeId);
	}

	public UUID addBookmark(final UUID placeId, final User user) {
		Place place = placeService.readPlaceById(placeId);

		Bookmark bookmark = findBookmarkById(user, place);
		bookmarkValidator.validateExistBookmark(bookmark);

		return bookmarkRepository.save(Bookmark.of(user, place)).getId();
	}

	public void deleteBookmark(final UUID placeId, final User user) {
		Place place = placeService.readPlaceById(placeId);

		Bookmark bookmark = findBookmarkById(user, place);
		bookmarkValidator.validateNotExistBookmark(bookmark);

		bookmarkRepository.deleteByUserAndPlace(user, place);
	}

	@Transactional(readOnly = true)
	public BookmarksResponseDto getBookmarks(final User user, final List<String> categories,
		final Pageable page) {
		Slice<BookmarkPlaceDto> bookmarkPlaceDtos = bookmarkRepository.findBookmarkPlacesInCategories(
			Category.typeCastStringToCategory(categories),
			user, page);

		return new BookmarksResponseDto(bookmarkPlaceDtos.getContent(), bookmarkPlaceDtos.hasNext());
	}

	@Transactional(readOnly = true)
	public Bookmark findBookmarkById(final User user, final Place place) {
		return bookmarkRepository.findByUserAndPlace(user, place);
	}
}
