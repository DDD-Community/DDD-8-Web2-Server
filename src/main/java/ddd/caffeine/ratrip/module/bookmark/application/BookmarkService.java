package ddd.caffeine.ratrip.module.bookmark.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.BookmarkException;
import ddd.caffeine.ratrip.module.bookmark.application.dto.RecommendByBookmarkAndLocationDto;
import ddd.caffeine.ratrip.module.bookmark.domain.Bookmark;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.BookmarkRepository;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.BookmarkByCategoryDao;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.RecommendByBookmarkDao;
import ddd.caffeine.ratrip.module.bookmark.presentation.dto.response.BookmarksByCategoryResponseDto;
import ddd.caffeine.ratrip.module.bookmark.presentation.dto.response.RecommendByBookmarkAndLocationResponseDto;
import ddd.caffeine.ratrip.module.bookmark.presentation.dto.response.RecommendByBookmarkAndRegionResponseDto;
import ddd.caffeine.ratrip.module.place.application.PlaceFeignService;
import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
	private final PlaceService placeService;
	private final PlaceFeignService placeFeignService;
	private final BookmarkRepository bookmarkRepository;

	public void createBookmark(User user, String placeKakaoId) {
		Place place = validateExistPlace(placeKakaoId);
		validateExistBookmark(user, place);
		bookmarkRepository.save(Bookmark.of(user, place));
		placeService.increaseBookmarkCount(place);
	}

	public boolean whetherBookmark(User user, String placeKakaoId) {
		Place place = validateExistPlace(placeKakaoId);
		return bookmarkRepository.findByPlaceIdAndUser(place.getId(), user).isPresent();
	}

	public BookmarksByCategoryResponseDto getBookmarksByCategory(User user, Category category, Pageable pageable) {
		Slice<BookmarkByCategoryDao> bookmarks = bookmarkRepository.findBookmarksByCategoryAndUser(category, user,
			pageable);

		return BookmarksByCategoryResponseDto.of(bookmarks.getContent(), bookmarks.hasNext());
	}

	public RecommendByBookmarkAndRegionResponseDto recommendByBookmarkAndRegion(User user, Region region,
		Pageable pageable) {
		Slice<RecommendByBookmarkDao> places = bookmarkRepository.findPlacesByRegionAndUser(region, user, pageable);

		return RecommendByBookmarkAndRegionResponseDto.of(places.getContent(), places.hasNext());
	}

	public RecommendByBookmarkAndLocationResponseDto recommendByBookmarkAndLocation(User user,
		RecommendByBookmarkAndLocationDto request, Pageable pageable) {
		Region region = placeFeignService.convertLongituteAndLatitudeToRegion(request.getLongitude(),
			request.getLatitude());

		Slice<RecommendByBookmarkDao> places = bookmarkRepository.findPlacesByRegionAndUser(region, user, pageable);

		return RecommendByBookmarkAndLocationResponseDto.of(places.getContent(), places.hasNext());
	}

	private Place validateExistPlace(String placeKakaoId) {
		return placeService.validateExistPlace(placeKakaoId);
	}

	private void validateExistBookmark(User user, Place place) {
		bookmarkRepository.findByPlaceIdAndUser(place.getId(), user).ifPresent(bookmark -> {
			throw new BookmarkException(ALREADY_EXIST_BOOKMARK_EXCEPTION);
		});
	}
}
