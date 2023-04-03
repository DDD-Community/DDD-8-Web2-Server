package ddd.caffeine.ratrip.module.place.domain.repository;

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
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.CategoryPlaceByRegionDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.PlaceBookmarkDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.PlaceDetailBookmarkDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.QCategoryPlaceByRegionDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.QPlaceBookmarkDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.QPlaceDetailBookmarkDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceQueryRepositoryImpl implements PlaceQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public PlaceBookmarkDao findByThirdPartyID(String thirdPartyID) {
		return jpaQueryFactory
			.select(new QPlaceBookmarkDao(place.id, place.name, place.category, place.address, place.location,
				place.imageLink, place.telephone, place.isUpdated, bookmark.isActivated))
			.from(place)
			.leftJoin(place.bookmarks, bookmark)
			.where(place.kakaoId.eq(thirdPartyID))
			.fetchOne();
	}

	@Override
	public PlaceDetailBookmarkDao findByUUID(UUID id) {
		return jpaQueryFactory
			.select(new QPlaceDetailBookmarkDao(place, bookmark.isActivated))
			.from(place)
			.leftJoin(place.bookmarks, bookmark)
			.where(place.id.eq(id))
			.fetchOne();
	}

	@Override
	public Slice<PlaceBookmarkDao> findPlacesInRegions(List<Region> regions, Pageable pageable) {
		List<PlaceBookmarkDao> contents = jpaQueryFactory
			.select(new QPlaceBookmarkDao(place.id, place.name, place.category, place.address, place.location,
				place.imageLink, place.telephone, place.isUpdated, bookmark.isActivated))
			.from(place)
			.leftJoin(place.bookmarks, bookmark)
			.where(regionsIn(regions))
			.orderBy(readOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return QuerydslUtils.toSlice(contents, pageable);
	}

	@Override
	public Slice<PlaceBookmarkDao> findPlacesInRegion(Region region, Pageable pageable) {
		List<PlaceBookmarkDao> contents = jpaQueryFactory
			.select(new QPlaceBookmarkDao(place.id, place.name, place.category, place.address, place.location,
				place.imageLink, place.telephone, place.isUpdated, bookmark.isActivated))
			.from(place)
			.leftJoin(place.bookmarks, bookmark)
			.where(regionsEq(region))
			.orderBy(readOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return QuerydslUtils.toSlice(contents, pageable);
	}

	@Override
	public Slice<CategoryPlaceByRegionDao> getCategoryPlacesByRegion(Region region, Category category,
		Pageable pageable) {
		List<CategoryPlaceByRegionDao> contents = jpaQueryFactory
			.select(new QCategoryPlaceByRegionDao(place.id, place.name))
			.from(place)
			.where(place.address.region.eq(region), place.category.eq(category))
			.orderBy(place.tripCount.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return QuerydslUtils.toSlice(contents, pageable);
	}

	@Override
	public Place findByKakaoId(String kakaoId) {
		return jpaQueryFactory
			.selectFrom(place)
			.where(place.kakaoId.eq(kakaoId))
			.fetchOne();
	}

	private BooleanExpression regionsIn(List<Region> region) {
		return region.isEmpty() ? null : place.address.region.in(region);
	}

	private BooleanExpression regionsEq(Region region) {
		return region == null ? null : place.address.region.eq(region);
	}

	private List<OrderSpecifier> readOrderSpecifiers(Pageable pageable) {
		List<OrderSpecifier> orders = new ArrayList<>();

		if (!isEmpty(pageable.getSort())) {
			for (Sort.Order order : pageable.getSort()) {
				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

				switch (order.getProperty()) {
					case "popular":
						OrderSpecifier<?> tripCount = QuerydslUtils
							.getSortedColumn(direction, place, "tripCount");
						orders.add(tripCount);
						break;
					default:
						break;
				}
			}
		}
		return orders;
	}
}