package ddd.caffeine.ratrip.module.travel_plan.application.dto;

import java.util.UUID;

import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ShortestPathDto {
	private final User user;
	private final UUID travelPlanId;
	private final UUID dayScheduleId;
	private final UUID placeId;

	public static ShortestPathDto of(final User user, final UUID travelPlanId, final UUID dayScheduleId,
		final UUID placeId) {
		return new ShortestPathDto(user, travelPlanId, dayScheduleId, placeId);
	}
}
