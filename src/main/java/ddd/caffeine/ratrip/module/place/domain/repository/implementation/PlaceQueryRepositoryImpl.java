package ddd.caffeine.ratrip.module.place.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.place.domain.QPlace.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.common.util.QuerydslUtils;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceQueryRepository;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.QRecommendByLocationDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.RecommendByLocationDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceQueryRepositoryImpl implements PlaceQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Slice<RecommendByLocationDao> findByRegion(Region region, Pageable pageable) {
		List<RecommendByLocationDao> contents = jpaQueryFactory
			.select(
				new QRecommendByLocationDao(
					place.id, place.kakaoId, place.name, place.address.detailed,
					place.category, place.imageLink
				)
			)
			.from(place)
			.where(
				place.address.region.eq(region),
				place.isDeleted.isFalse()
			)
			.orderBy(place.totalScore.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return QuerydslUtils.toSlice(contents, pageable);
	}
}
