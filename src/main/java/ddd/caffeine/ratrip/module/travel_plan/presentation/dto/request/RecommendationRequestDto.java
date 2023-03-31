package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.request;

import ddd.caffeine.ratrip.module.travel_plan.application.dto.RecommendationPathDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendationRequestDto {

    @Schema(description = "최단 거리의 기준이 되는 장소의 ID", example = "11edb281-8a52-2d4f-9465-1970980a80db")
    @NotBlank(message = "PlaceId must not be blank")
    private UUID placeId;

    public RecommendationPathDto toServiceDto(final User user, final UUID travelPlanId, final UUID dayScheduleId) {
        return RecommendationPathDto.of(user, travelPlanId, dayScheduleId, placeId);
    }
}
