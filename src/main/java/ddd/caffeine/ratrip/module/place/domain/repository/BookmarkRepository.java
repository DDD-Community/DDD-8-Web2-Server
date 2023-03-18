package ddd.caffeine.ratrip.module.place.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.place.domain.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.BookmarkId;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId>, BookmarkQueryRepository {
}
