package ddd.caffeine.ratrip.module.place.domain.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.place.domain.bookmark.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.bookmark.BookmarkId;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId>, BookmarkQueryRepository {
}
