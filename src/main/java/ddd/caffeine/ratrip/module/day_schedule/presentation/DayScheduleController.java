package ddd.caffeine.ratrip.module.day_schedule.presentation;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.day_schedule.application.DayScheduleService;
import ddd.caffeine.ratrip.module.day_schedule.presentation.dto.request.RecommendationPathRequestDto;
import ddd.caffeine.ratrip.module.day_schedule.presentation.dto.response.RecommendationPathResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DayScheduleController {

	private final DayScheduleService dayScheduleService;

	@Operation(summary = "[인증] DaySchedule 내에서 선택한 여행지를 출발 지점으로 한 경로 추천")
	@GetMapping("/travel-plans/{travel_plan_id}/day-schedules/{day_schedule_id}/recommendation-path")
	public ResponseEntity<RecommendationPathResponseDto> getRecommendationPath(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") UUID travelPlanId,
		@PathVariable("day_schedule_id") UUID dayScheduleId,
		@Valid @RequestBody RecommendationPathRequestDto request) {

		return ResponseEntity.ok(
			dayScheduleService.getRecommendationPath(request.toServiceDto(user, travelPlanId, dayScheduleId)));
	}

}
