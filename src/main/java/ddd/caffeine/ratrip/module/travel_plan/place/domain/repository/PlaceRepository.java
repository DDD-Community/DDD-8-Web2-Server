package ddd.caffeine.ratrip.module.travel_plan.place.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.travel_plan.place.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceQueryRepository {
	Optional<Place> findByKakaoId(String kakaoId);
}
