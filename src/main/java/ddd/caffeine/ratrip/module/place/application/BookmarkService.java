package ddd.caffeine.ratrip.module.place.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.bookmark.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.bookmark.BookmarkId;
import ddd.caffeine.ratrip.module.place.domain.bookmark.repository.BookmarkRepository;
import ddd.caffeine.ratrip.module.place.domain.bookmark.repository.dao.BookMarkPlaceDao;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Category;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlaceResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
	private final BookmarkRepository bookmarkRepository;
	private final BookmarkValidator bookmarkValidator;

	public BookmarkResponseDto readBookmark(User user, Place place) {
		BookmarkId bookmarkId = new BookmarkId(user.getId(), place.getId());
		Optional<Bookmark> bookmark = bookmarkRepository.findById(bookmarkId);
		if (bookmark.isEmpty()) {
			return BookmarkResponseDto.hasBookmarkFalse();
		}
		return BookmarkResponseDto.builder()
			.isBookmarked(bookmark.get().isActivated())
			.build();
	}

	public BookmarkResponseDto createBookmark(User user, Place place) {
		BookmarkId bookmarkId = new BookmarkId(user.getId(), place.getId());
		Bookmark bookmark = Bookmark.of(bookmarkId, user, place);
		bookmarkRepository.save(bookmark);

		return BookmarkResponseDto.builder()
			.isBookmarked(bookmark.isActivated())
			.build();
	}

	public BookmarkResponseDto changeBookmarkState(User user, Place place) {
		BookmarkId bookmarkId = new BookmarkId(user.getId(), place.getId());
		Bookmark bookmark = bookmarkValidator.validateExistOptionalBookmark(
			bookmarkRepository.findById(bookmarkId));
		bookmark.changeBookmarkState();

		return BookmarkResponseDto.builder()
			.isBookmarked(bookmark.isActivated())
			.build();
	}

	public BookmarkPlaceResponseDto getBookmarks(User user, List<String> categories,
		Pageable page) {
		Slice<BookMarkPlaceDao> bookmarkPlaceDtos = bookmarkRepository.findBookmarkPlacesInCategories(
			Category.createCategories(categories),
			user, page);

		return new BookmarkPlaceResponseDto(bookmarkPlaceDtos.getContent(), bookmarkPlaceDtos.hasNext());
	}
}
