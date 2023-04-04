package ddd.caffeine.ratrip.module.travel_plan.presentation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.travel_plan.application.TravelPlanService;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/travel-plans")
public class TravelPlanController {
	private final TravelPlanService travelPlanService;

	// @Operation(summary = "[인증] 여행 계획 만들기 API")
	// @PostMapping
	// public ResponseEntity<TravelPlanResponseDto> createTravelPlan(
	// 	@Parameter(hidden = true) @AuthenticationPrincipal User user,
	// 	@Valid @RequestBody TravelPlanInitRequestDto request) {
	// 	TravelPlanResponseDto response = travelPlanService.createTravelPlan(
	// 		request.mapByTravelPlan(), user);
	// 	return ResponseEntity.ok(response);
	// }
}
