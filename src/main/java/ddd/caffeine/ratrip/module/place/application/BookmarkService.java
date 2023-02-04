package ddd.caffeine.ratrip.module.place.application;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.bookmark.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.bookmark.BookmarkId;
import ddd.caffeine.ratrip.module.place.domain.bookmark.repository.BookmarkRepository;
import ddd.caffeine.ratrip.module.place.domain.bookmark.repository.dao.BookMarkPlaceDao;
import ddd.caffeine.ratrip.module.place.domain.bookmark.repository.dao.BookmarkPlaceByRegionDao;
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
		Bookmark bookmark = bookmarkRepository.findByBookmarkId(bookmarkId);
		if (bookmark == null) {
			return BookmarkResponseDto.hasBookmarkFalse();
		}
		return new BookmarkResponseDto(bookmark.isActivated());
	}

	public BookmarkResponseDto createBookmark(User user, Place place) {
		BookmarkId bookmarkId = new BookmarkId(user.getId(), place.getId());
		boolean exist = bookmarkRepository.existsByBookmarkId(bookmarkId);
		bookmarkValidator.validateNotExistBookmark(exist);
		Bookmark bookmark = Bookmark.of(user, place);
		bookmarkRepository.save(bookmark);

		return new BookmarkResponseDto(bookmark.isActivated());
	}

	public BookmarkResponseDto changeBookmarkState(User user, Place place) {
		BookmarkId bookmarkId = new BookmarkId(user.getId(), place.getId());
		Bookmark bookmark = bookmarkRepository.findByBookmarkId(bookmarkId);
		bookmarkValidator.validateExistBookmark(bookmark);
		bookmark.changeBookmarkState();

		return new BookmarkResponseDto(bookmark.isActivated());
	}

	public BookmarkPlaceResponseDto getBookmarks(User user, List<String> categories,
		Pageable page) {
		Slice<BookMarkPlaceDao> bookmarkPlaceDtos = bookmarkRepository.findBookmarkPlacesInCategories(
			Category.createCategories(categories),
			user, page);

		return new BookmarkPlaceResponseDto(bookmarkPlaceDtos.getContent(), bookmarkPlaceDtos.hasNext());
	}

	public Slice<BookmarkPlaceByRegionDao> getBookmarkPlacesByRegion(User user, Region region, Pageable pageable) {
		return bookmarkRepository.findBookmarkPlacesByRegion(user, region, pageable);
	}
}
