package ddd.caffeine.ratrip.module.memo.domain.repository;

import java.util.List;
import java.util.Optional;

import ddd.caffeine.ratrip.module.memo.domain.Memo;
import ddd.caffeine.ratrip.module.memo.domain.repository.dao.MemoDao;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface MemoQueryRepository {
	List<MemoDao> findMemoDaoByDayPlanIdAndUser(Long dayPlanId, User user);

	List<Memo> findByDayPlanIdAndUser(Long dayPlanId, User user);

	Optional<Memo> findByIdAndUser(Long memoId, User user);

	Optional<Memo> findByDayPlanIdAndPlaceIdAndUser(Long id, User user, Place place);
}
