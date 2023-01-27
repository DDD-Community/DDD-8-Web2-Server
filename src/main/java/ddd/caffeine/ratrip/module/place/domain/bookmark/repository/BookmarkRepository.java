package ddd.caffeine.ratrip.module.place.domain.bookmark.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.place.domain.bookmark.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, UUID>, BookmarkQueryRepository {
}
