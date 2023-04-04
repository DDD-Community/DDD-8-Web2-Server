package ddd.caffeine.ratrip.module.place.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ddd.caffeine.ratrip.module.place.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceQueryRepository {

}
