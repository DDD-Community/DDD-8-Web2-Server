package ddd.caffeine.ratrip.module.bookmark.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.bookmark.domain.QBookmark.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.common.util.QuerydslUtils;
import ddd.caffeine.ratrip.module.bookmark.domain.Bookmark;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.BookmarkQueryRepository;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.BookmarkByCategoryDao;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.QBookmarkByCategoryDao;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.QRecommendByBookmarkDao;
import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.RecommendByBookmarkDao;
import ddd.caffeine.ratrip.module.travel_plan.place.domain.Category;
import ddd.caffeine.ratrip.module.travel_plan.place.domain.Region;
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

	@Override
	public Slice<BookmarkByCategoryDao> findBookmarksByCategoryAndUser(Category category, User user,
		Pageable pageable) {
		List<BookmarkByCategoryDao> contents = jpaQueryFactory
			.select(
				new QBookmarkByCategoryDao(
					bookmark.id, bookmark.place.kakaoId, bookmark.place.name, bookmark.place.address.detailed,
					bookmark.place.category
				)
			)
			.from(bookmark)
			.where(
				bookmark.user.eq(user),
				bookmark.place.category.eq(category),
				bookmark.isDeleted.isFalse()
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return QuerydslUtils.toSlice(contents, pageable);
	}

	@Override
	public Slice<RecommendByBookmarkDao> findPlacesByRegionAndUser(Region region, User user, Pageable pageable) {
		List<RecommendByBookmarkDao> contents = jpaQueryFactory
			.select(
				new QRecommendByBookmarkDao(
					bookmark.place.id, bookmark.place.kakaoId, bookmark.place.name, bookmark.place.address.detailed,
					bookmark.place.category, bookmark.place.imageLink
				)
			)
			.from(bookmark)
			.where(
				bookmark.user.eq(user),
				bookmark.place.address.region.eq(region),
				bookmark.isDeleted.isFalse()
			)
			.orderBy(bookmark.place.totalScore.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return QuerydslUtils.toSlice(contents, pageable);
	}
}
