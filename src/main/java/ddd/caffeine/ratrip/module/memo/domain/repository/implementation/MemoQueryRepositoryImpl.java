package ddd.caffeine.ratrip.module.memo.domain.repository.implementation;

import static ddd.caffeine.ratrip.module.memo.domain.QMemo.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import ddd.caffeine.ratrip.module.memo.domain.Memo;
import ddd.caffeine.ratrip.module.memo.domain.repository.MemoQueryRepository;
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
}
