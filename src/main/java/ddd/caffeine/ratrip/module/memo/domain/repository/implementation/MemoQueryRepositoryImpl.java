package ddd.caffeine.ratrip.module.memo.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.memo.domain.QMemo.*;

import java.util.List;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.memo.domain.Memo;
import ddd.caffeine.ratrip.module.memo.domain.repository.MemoQueryRepository;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemoQueryRepositoryImpl implements MemoQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Memo> findByDayPlanIdAndUser(Long dayPlanId, User user) {
		return jpaQueryFactory.selectFrom(memo)
			.where(memo.dayPlan.id.eq(dayPlanId), memo.dayPlan.user.eq(user))
			.fetch();
	}

	@Override
	public Optional<Memo> findByIdAndUser(Long memoId, User user) {
		return Optional.ofNullable(jpaQueryFactory
			.selectFrom(memo)
			.where(
				memo.id.eq(memoId),
				memo.user.eq(user)
			)
			.fetchOne());
	}

	@Override
	public Optional<Memo> findByDayPlanIdAndPlaceIdAndUser(Long id, User user, Place place) {
		return Optional.ofNullable(jpaQueryFactory
			.selectFrom(memo)
			.where(
				memo.dayPlan.id.eq(id),
				memo.dayPlan.user.eq(user),
				memo.place.eq(place)
			)
			.fetchOne());
	}

}
