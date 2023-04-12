package ddd.caffeine.ratrip.module.bookmark.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.bookmark.domain.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkQueryRepository {
}
