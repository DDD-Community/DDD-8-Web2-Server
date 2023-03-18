package ddd.caffeine.ratrip.module.place.domain.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ddd.caffeine.ratrip.common.util.QuerydslUtils;
import ddd.caffeine.ratrip.module.place.domain.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.BookmarkId;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookMarkPlaceDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookmarkPlaceByRegionDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.QBookMarkPlaceDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.QBookmarkPlaceByRegionDao;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ddd.caffeine.ratrip.module.place.domain.QBookmark.bookmark;
import static ddd.caffeine.ratrip.module.place.domain.QPlace.place;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Bookmark findByBookmarkId(BookmarkId id) {
        return jpaQueryFactory
                .selectFrom(bookmark)
                .where(
                        bookmarkIdEq(id),
                        bookmark.isDeleted.isFalse()
                )
                .fetchOne();
    }

    @Override
    public Slice<BookMarkPlaceDao> findBookmarkPlacesInCategories(List<Category> categories, User user,
                                                                  Pageable pageable) {
        List<BookMarkPlaceDao> contents = jpaQueryFactory
                .select(new QBookMarkPlaceDao(place.id, place.name, place.address.detailed, place.imageLink,
                        place.category)
                )
                .from(bookmark)
                .innerJoin(bookmark.place, place)
                .where(
                        bookmark.user.eq(user),
                        bookmark.isActivated.isTrue(),
                        bookmark.isDeleted.isFalse(),
                        categoriesIn(categories)
                )
                .orderBy(readOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return QuerydslUtils.toSlice(contents, pageable);
    }

    @Override
    public boolean existsByBookmarkId(BookmarkId id) {
        return jpaQueryFactory.selectFrom(bookmark)
                .where(
                        bookmarkIdEq(id)
                )
                .fetchFirst() != null;
    }

    @Override
    public Long deleteBookMark(Bookmark entity) {
        return jpaQueryFactory
                .delete(bookmark)
                .where(bookmark.eq(entity))
                .execute();
    }

    @Override
    public Slice<BookmarkPlaceByRegionDao> findBookmarkPlacesByRegion(User user, Region region, Pageable pageable) {
        List<BookmarkPlaceByRegionDao> contents = jpaQueryFactory
                .select(new QBookmarkPlaceByRegionDao(place.id, place.name, place.imageLink))
                .from(bookmark)
                .innerJoin(bookmark.place, place)
                .where(
                        bookmark.user.eq(user),
                        bookmark.isActivated.isTrue(),
                        bookmark.isDeleted.isFalse(),
                        place.address.region.eq(region)
                )
                .orderBy(place.numberOfTrips.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return QuerydslUtils.toSlice(contents, pageable);
    }

    @Override
    public List<Bookmark> findByUserId(UUID id) {
        return jpaQueryFactory
                .selectFrom(bookmark)
                .where(
                        bookmark.user.id.eq(id)
                )
                .fetch();
    }

    private BooleanExpression categoriesIn(List<Category> categories) {
        return categories.isEmpty() ? null : place.category.in(categories);
    }

    private BooleanExpression bookmarkIdEq(BookmarkId bookmarkId) {
        return bookmarkId == null ? null :
                bookmark.user.id.eq(bookmarkId.getUser()).and(
                        bookmark.place.id.eq(bookmarkId.getPlace()));
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
