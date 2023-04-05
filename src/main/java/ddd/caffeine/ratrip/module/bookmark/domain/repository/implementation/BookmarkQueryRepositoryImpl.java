package ddd.caffeine.ratrip.module.bookmark.domain.repository.implementation;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.place.domain.repository.PlaceQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements PlaceQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
}
