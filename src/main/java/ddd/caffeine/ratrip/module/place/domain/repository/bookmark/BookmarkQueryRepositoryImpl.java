package ddd.caffeine.ratrip.module.place.domain.repository.bookmark;

import static ddd.caffeine.ratrip.module.place.domain.QBookmark.*;
import static ddd.caffeine.ratrip.module.place.domain.QPlace.*;
import static org.springframework.util.ObjectUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.common.util.QuerydslUtils;
import ddd.caffeine.ratrip.module.place.domain.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookMarkPlaceDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.QBookMarkPlaceDao;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Bookmark findByUserAndPlace(User user, Place place) {
		return jpaQueryFactory.selectFrom(bookmark)
			.where(bookmark.user.eq(user), bookmark.place.eq(place))
			.fetchOne();
	}

	@Override
	public Slice<BookMarkPlaceDao> findBookmarkPlacesInCategories(List<Category> categories, User user,
		Pageable pageable) {
		List<BookMarkPlaceDao> contents = jpaQueryFactory
			.select(new QBookMarkPlaceDao(bookmark.id, place.name, place.address.detailed, place.imageLink,
				place.category)) //TODO - BookmarksResponseDto로 한번에 처리할 수 있을 것 같은데..
			.from(bookmark)
			.join(bookmark.place, place)
			.where(
				bookmark.user.eq(user),
				bookmark.isActivated.isTrue(),
				categoriesIn(categories)
			)
			.orderBy(readOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return QuerydslUtils.toSlice(contents, pageable);
	}

	@Override
	public boolean existsByUserIdAndPlaceId(UUID userId, UUID placeId) {
		return jpaQueryFactory.selectFrom(bookmark)
			.where(bookmark.user.id.eq(userId), bookmark.place.id.eq(placeId))
			.fetchFirst() != null;
	}

	@Override
	public Long deleteBookMark(Bookmark entity) {
		return jpaQueryFactory
			.delete(bookmark)
			.where(bookmark.eq(entity))
			.execute();
	}

	private BooleanExpression categoriesIn(List<Category> categories) {
		return categories.isEmpty() ? null : place.category.in(categories);
	}

	//TODO - 리팩토링 고려 / Switch 문인데 어떻게 여러 조건을 처리하지?
	private List<OrderSpecifier> readOrderSpecifiers(Pageable pageable) {
		List<OrderSpecifier> orders = new ArrayList<>();

		if (!isEmpty(pageable.getSort())) {
			for (Sort.Order order : pageable.getSort()) {
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

				switch (order.getProperty()) {
					case "createdAt":
						OrderSpecifier<?> createdAt = QuerydslUtils
							.getSortedColumn(direction, place, "createdAt");
						orders.add(createdAt);
						break;
					default:
						break;
				}
			}
		}
		return orders;
	}
}
