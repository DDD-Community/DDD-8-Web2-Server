package ddd.caffeine.ratrip.module.memo.domain.repository.implementation;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.memo.domain.repository.MemoQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemoQueryRepositoryImpl implements MemoQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
}
