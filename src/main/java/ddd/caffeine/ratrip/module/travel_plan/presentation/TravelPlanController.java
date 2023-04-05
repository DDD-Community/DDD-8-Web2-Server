package ddd.caffeine.ratrip.module.travel_plan.presentation;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.travel_plan.application.TravelPlanService;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.request.CreateTravelPlanRequestDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.CreateTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.TerminatedTravelPlansResponseDto;
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

	@Operation(summary = "[인증] 여행 계획 생성")
	@PostMapping
	public ResponseEntity<CreateTravelPlanResponseDto> createTravelPlan(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @RequestBody CreateTravelPlanRequestDto request) {

		return ResponseEntity.ok(travelPlanService.createTravelPlan(user, request.toServiceDto()));
	}

	@Operation(summary = "[인증] 여행 계획 종료")
	@PatchMapping("/{travel_plan_id}")
	public ResponseEntity<String> endTravelPlan(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") Long travelPlanId) {

		travelPlanService.endTravelPlan(user, travelPlanId);
		return ResponseEntity.ok("End Travel Plan Success");
	}

	@Operation(summary = "[인증] 진행 했던 모든 여행 계획 페이지네이션 조회")
	@GetMapping
	public ResponseEntity<TerminatedTravelPlansResponseDto> getTerminatedTravelPlans(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PageableDefault(size = 8, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

		return ResponseEntity.ok(travelPlanService.getTerminatedTravelPlans(user, pageable));
	}
}
