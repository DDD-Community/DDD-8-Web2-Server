package ddd.caffeine.ratrip.module.memo.domain.repository;

import java.util.List;
import java.util.Optional;

import ddd.caffeine.ratrip.module.memo.domain.Memo;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface MemoQueryRepository {
	List<Memo> findByDayPlanIdAndUser(Long dayPlanId, User user);

	Optional<Memo> findByIdAndUser(Long memoId, User user);
}
