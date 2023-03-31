package ddd.caffeine.ratrip.module.travel_plan.application.dto;

import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendationPathDto {
    private final User user;
    private final UUID travelPlanId;
    private final UUID dayScheduleId;
    private final UUID placeId;

    public static RecommendationPathDto of(final User user, final UUID travelPlanId, final UUID dayScheduleId,
                                           final UUID placeId) {
        return new RecommendationPathDto(user, travelPlanId, dayScheduleId, placeId);
    }
}
