package ddd.caffeine.ratrip.module.bookmark.domain.repository;

import java.util.Optional;

import ddd.caffeine.ratrip.module.bookmark.domain.Bookmark;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface BookmarkQueryRepository {
	Optional<Bookmark> findByPlaceIdAndUser(Long placeId, User user);
}
