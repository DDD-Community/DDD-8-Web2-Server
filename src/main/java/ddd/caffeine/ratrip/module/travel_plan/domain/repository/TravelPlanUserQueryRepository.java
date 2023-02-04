package ddd.caffeine.ratrip.module.travel_plan.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanUser;
import ddd.caffeine.ratrip.module.user.domain.User;

public interface TravelPlanUserQueryRepository {
	boolean existByUserAndTravelPlanUUID(User user, UUID travelPlanUUID);

	TravelPlanUser findByUserUnfinishedTravel(User user);

	Slice<TravelPlanUser> findByUser(User user, Pageable pageable);

	Region findOngoingTravelPlanUserRegionByUser(User user);
}
