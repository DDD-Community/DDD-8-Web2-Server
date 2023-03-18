package ddd.caffeine.ratrip.module.place.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import ddd.caffeine.ratrip.module.place.domain.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.BookmarkId;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookMarkPlaceDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookmarkPlaceByRegionDao;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface BookmarkQueryRepository {
	Bookmark findByBookmarkId(BookmarkId id);

	boolean existsByBookmarkId(BookmarkId id);

	Slice<BookMarkPlaceDao> findBookmarkPlacesInCategories(List<Category> categories, User user, Pageable pageable);

	Long deleteBookMark(Bookmark entity);

	Slice<BookmarkPlaceByRegionDao> findBookmarkPlacesByRegion(User user, Region region, Pageable pageable);

	List<Bookmark> findByUserId(UUID id);

}
