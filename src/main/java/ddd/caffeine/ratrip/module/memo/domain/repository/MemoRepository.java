package ddd.caffeine.ratrip.module.memo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.memo.domain.Memo;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long>, MemoQueryRepository {
}
