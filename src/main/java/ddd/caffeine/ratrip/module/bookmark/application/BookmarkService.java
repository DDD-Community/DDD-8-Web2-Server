package ddd.caffeine.ratrip.module.bookmark.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		bookmarkRepository.save(Bookmark.of(user, place));
	}

	private Place validateExistPlace(Long placeId) {
		return placeService.validateExistPlace(placeId);
	}
}
