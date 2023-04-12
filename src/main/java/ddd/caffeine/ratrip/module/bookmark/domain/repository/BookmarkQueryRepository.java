package ddd.caffeine.ratrip.module.bookmark.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import ddd.caffeine.ratrip.module.bookmark.domain.Bookmark;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.BookmarkByCategoryDao;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.RecommendByBookmarkDao;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface BookmarkQueryRepository {
	Optional<Bookmark> findByPlaceIdAndUser(Long placeId, User user);

	Slice<BookmarkByCategoryDao> findBookmarksByCategoryAndUser(Category category, User user, Pageable pageable);

	Slice<RecommendByBookmarkDao> findPlacesByRegionAndUser(Region region, User user, Pageable pageable);
}
