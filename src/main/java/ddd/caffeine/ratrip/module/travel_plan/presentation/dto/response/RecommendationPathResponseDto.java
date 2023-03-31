package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendationPathResponseDto {
    private List<PlaceNameResponse> placeNameResponses;

    public static RecommendationPathResponseDto of(final List<PlaceNameResponse> placeNameResponses) {
        return new RecommendationPathResponseDto(placeNameResponses);
    }
}
