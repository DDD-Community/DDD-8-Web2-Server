package ddd.caffeine.ratrip.module.place.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.module.place.domain.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.bookmark.BookmarkRepository;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookMarkPlaceDao;
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

	public BookmarkResponseDto readBookmarkModel(User user, Place place) {
		Bookmark bookmark = readBookmark(user, place);
		if (bookmark == null) {
			Bookmark entity = Bookmark.of(user, place);
			bookmarkRepository.save(entity);
			return new BookmarkResponseDto(entity);
		}
		return new BookmarkResponseDto(bookmark);
	}

	public BookmarkResponseDto changeBookmarkState(UUID bookmarkUUID) {
		Optional<Bookmark> bookmark = bookmarkRepository.findById(bookmarkUUID);
		bookmarkValidator.validateExistOptionalBookmark(bookmark);
		bookmark.get().changeBookmarkState();

		return new BookmarkResponseDto(bookmark.get());
	}

	public BookmarkPlaceResponseDto getBookmarks(User user, List<String> categories,
		Pageable page) {
		Slice<BookMarkPlaceDao> bookmarkPlaceDtos = bookmarkRepository.findBookmarkPlacesInCategories(
			Category.createCategories(categories),
			user, page);

		return new BookmarkPlaceResponseDto(bookmarkPlaceDtos.getContent(), bookmarkPlaceDtos.hasNext());
	}

	/**
	 * 북마크 엔티티를 조회 하는 메서드.
	 */
	private Bookmark readBookmark(User user, Place place) {
		return bookmarkRepository.findByUserAndPlace(user, place);
	}
}
