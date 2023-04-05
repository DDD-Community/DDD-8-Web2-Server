package ddd.caffeine.ratrip.module.memo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ddd.caffeine.ratrip.module.memo.domain.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long>, MemoQueryRepository {
}
