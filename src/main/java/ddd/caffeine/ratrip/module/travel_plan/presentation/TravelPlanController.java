package ddd.caffeine.ratrip.module.travel_plan.presentation;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.travel_plan.application.TravelPlanService;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.request.CreateTravelPlanRequestDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.CreateTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/travel-plans")
public class TravelPlanController {
	private final TravelPlanService travelPlanService;

	@Operation(summary = "[인증] 여행 계획 만들기 API")
	@PostMapping
	public ResponseEntity<CreateTravelPlanResponseDto> createTravelPlan(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @RequestBody CreateTravelPlanRequestDto request) {
		CreateTravelPlanResponseDto response = travelPlanService.createTravelPlan(user, request.toServiceDto());
		return ResponseEntity.ok(response);
	}
}
