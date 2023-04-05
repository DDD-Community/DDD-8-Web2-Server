package ddd.caffeine.ratrip.module.bookmark.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.bookmark.domain.QBookmark.*;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.bookmark.domain.Bookmark;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.BookmarkQueryRepository;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Bookmark> findByPlaceIdAndUser(Long placeId, User user) {
		return Optional.ofNullable(jpaQueryFactory
			.selectFrom(bookmark)
			.where(
				bookmark.place.id.eq(placeId),
				bookmark.user.eq(user)
			)
			.fetchOne()
		);
	}
}
