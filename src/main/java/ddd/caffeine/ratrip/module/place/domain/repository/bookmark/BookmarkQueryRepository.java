package ddd.caffeine.ratrip.module.place.domain.repository.bookmark;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import ddd.caffeine.ratrip.module.place.domain.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookMarkPlaceDao;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface BookmarkQueryRepository {
	Bookmark findByUserAndPlace(User user, Place place);

	boolean existsByUserIdAndPlaceId(UUID userId, UUID placeId);

	Slice<BookMarkPlaceDao> findBookmarkPlacesInCategories(List<Category> categories, User user, Pageable pageable);

	Long deleteBookMark(Bookmark entity);
}
