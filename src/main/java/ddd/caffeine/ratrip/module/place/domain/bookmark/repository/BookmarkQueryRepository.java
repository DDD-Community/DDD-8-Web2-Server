package ddd.caffeine.ratrip.module.place.domain.bookmark.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.domain.bookmark.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.bookmark.BookmarkId;
import ddd.caffeine.ratrip.module.place.domain.bookmark.repository.dao.BookMarkPlaceDao;
import ddd.caffeine.ratrip.module.place.domain.bookmark.repository.dao.BookmarkPlaceByRegionDao;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface BookmarkQueryRepository {
	Bookmark findByBookmarkId(BookmarkId id);

	boolean existsByBookmarkId(BookmarkId id);

	Slice<BookMarkPlaceDao> findBookmarkPlacesInCategories(List<Category> categories, User user, Pageable pageable);

	Long deleteBookMark(Bookmark entity);

	Slice<BookmarkPlaceByRegionDao> findBookmarkPlacesByRegion(User user, Region region, Pageable pageable);

	List<Bookmark> findByUserId(UUID id);

}
