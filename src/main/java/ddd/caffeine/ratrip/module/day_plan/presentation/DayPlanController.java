package ddd.caffeine.ratrip.module.day_plan.presentation;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.day_plan.application.DayPlanService;
import ddd.caffeine.ratrip.module.day_plan.presentation.dto.request.DayPlansRequestDto;
import ddd.caffeine.ratrip.module.day_plan.presentation.dto.response.DayPlansResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/day-plans")
public class DayPlanController {
	private final DayPlanService dayPlanService;

	@Operation(summary = "[인증] 하루 일정 전체 조회")
	@GetMapping
	public ResponseEntity<DayPlansResponseDto> getDayPlans(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @RequestBody DayPlansRequestDto request) {

		return ResponseEntity.ok(dayPlanService.getDayPlans(user, request.toServiceDto()));
	}
}
