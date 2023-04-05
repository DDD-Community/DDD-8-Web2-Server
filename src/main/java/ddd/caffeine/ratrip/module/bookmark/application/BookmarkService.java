package ddd.caffeine.ratrip.module.bookmark.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.BookmarkException;
import ddd.caffeine.ratrip.module.bookmark.domain.Bookmark;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.BookmarkRepository;
import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
	private final PlaceService placeService;
	private final BookmarkRepository bookmarkRepository;

	public void createBookmark(User user, Long placeId) {
		Place place = validateExistPlace(placeId);
		validateExistBookmark(user, place);
		bookmarkRepository.save(Bookmark.of(user, place));
		placeService.increaseBookmarkCount(place);
	}

	public boolean whetherBookmark(User user, Long placeId) {
		Place place = validateExistPlace(placeId);
		return bookmarkRepository.findByPlaceIdAndUser(place.getId(), user).isPresent();
	}

	private Place validateExistPlace(Long placeId) {
		return placeService.validateExistPlace(placeId);
	}

	private void validateExistBookmark(User user, Place place) {
		bookmarkRepository.findByPlaceIdAndUser(place.getId(), user)
			.orElseThrow(() -> new BookmarkException(ALREADY_EXIST_BOOKMARK_EXCEPTION));
	}
}
