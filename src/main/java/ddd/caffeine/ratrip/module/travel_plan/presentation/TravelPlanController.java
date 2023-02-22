package ddd.caffeine.ratrip.module.travel_plan.presentation;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.common.annotation.NullableUUIDFormat;
import ddd.caffeine.ratrip.common.annotation.UUIDFormat;
import ddd.caffeine.ratrip.module.travel_plan.application.TravelPlanService;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanAccessOption;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DayScheduleAccessOption;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.request.DayScheduleAddPlaceRequestDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.request.DaySchedulePlaceSequenceDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.request.DayScheduleUpdatePlaceRequestDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.response.DayScheduleInTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.response.DaySchedulePlaceResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.response.DayScheduleResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.request.TravelPlanInitRequestDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.LatestTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.MyTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.TravelPlanResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/travel-plans")
public class TravelPlanController {

	private final TravelPlanService travelPlanService;

	@Operation(summary = "[인증] 진행 했던 모든 여행계획 불러오기")
	@GetMapping
	public ResponseEntity<MyTravelPlanResponseDto> readAllTravelPlanApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		MyTravelPlanResponseDto response = travelPlanService.readAllTravelPlanByUserPagination(user, pageable);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 여행 계획 만들기 API")
	@PostMapping
	public ResponseEntity<TravelPlanResponseDto> makeTravelPlanApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @RequestBody TravelPlanInitRequestDto request) {
		TravelPlanResponseDto response = travelPlanService.makeTravelPlan(
			request.mapByTravelPlan(), user);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 여행 계획 종료 API (상태 변경)")
	@PatchMapping("/{travel_plan_id}")
	public ResponseEntity<TravelPlanResponseDto> endTravelPlanApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") @UUIDFormat String travelPlanUUID) {

		TravelPlanResponseDto response = travelPlanService.endTravelPlan(
			new TravelPlanAccessOption(user, travelPlanUUID));
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증 (개발용)] 여행 계획 삭제 API")
	@DeleteMapping("/{travel_plan_id}")
	public ResponseEntity<String> deleteTravelPlanApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") @UUIDFormat String travelPlanUUID) {
		travelPlanService.TEMP_deleteTravelPlan(travelPlanUUID, user);
		return ResponseEntity.ok("DELETE TO SUCCESS");
	}

	@Operation(summary = "[인증]최근여행 계획 정보 불러오기 API")
	@GetMapping("latest")
	public ResponseEntity<LatestTravelPlanResponseDto> readTravelPlanLatestApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user) {
		LatestTravelPlanResponseDto response = travelPlanService.readRecentTravelPlanByUser(user);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 여행계획 중 하루일정들 모두 불러오기 API")
	@GetMapping("/{travel_plan_id}/day-schedules")
	public ResponseEntity<DayScheduleInTravelPlanResponseDto> readDaySchedulesInTravelPlanApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") @UUIDFormat String travelPlanUUID) {

		DayScheduleInTravelPlanResponseDto response = travelPlanService.readDaySchedulesInTravelPlan(
			new TravelPlanAccessOption(user, travelPlanUUID));

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 하루 일정 읽기 API")
	@GetMapping("/{travel_plan_id}/day-schedules/{day_schedule_id}")
	public ResponseEntity<DayScheduleResponseDto> readDayScheduleByUUIDApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") @UUIDFormat String travelPlanUUID,
		@PathVariable("day_schedule_id") @UUIDFormat String dayScheduleUUID,
		@RequestParam(name = "place-id", required = false) @NullableUUIDFormat String placeUUID) {

		DayScheduleResponseDto response = travelPlanService.readScheduleByUUID(
			new DayScheduleAccessOption(user, travelPlanUUID, dayScheduleUUID), placeUUID);

		return ResponseEntity.ok(response);
	}

	/**
	 * @return : day-schedule-place UUID
	 */
	@Operation(summary = "[인증] 일정 장소 추가 API (생성)")
	@ApiResponse(description = "장소 추가 성공 시, ID 반환")
	@PostMapping("/{travel_plan_id}/day-schedules/{day_schedule_id}/day-schedule-places")
	public ResponseEntity<DaySchedulePlaceResponseDto> addPlaceInDayScheduleApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") @UUIDFormat String travelPlanUUID,
		@PathVariable("day_schedule_id") @UUIDFormat String dayScheduleUUID,
		@Valid @RequestBody DayScheduleAddPlaceRequestDto request) {
		DaySchedulePlaceResponseDto response = travelPlanService.addPlaceInDaySchedule(
			new DayScheduleAccessOption(user, travelPlanUUID, dayScheduleUUID),
			request.getId(), request.getMemo());

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 일정 내의 장소 순서 업데이트 API")
	@PutMapping("/{travel_plan_id}/day-schedules/{day_schedule_id}/day-schedule-places")
	public ResponseEntity<String> updatePlacesSequenceInDayScheduleApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") @UUIDFormat String travelPlanUUID,
		@PathVariable("day_schedule_id") @UUIDFormat String dayScheduleUUID,
		@Valid @RequestBody DaySchedulePlaceSequenceDto request) {

		travelPlanService.updatePlacesSequenceInDaySchedule(
			new DayScheduleAccessOption(user, travelPlanUUID, dayScheduleUUID),
			request.readUUIDs());
		return ResponseEntity.ok("SUCCESS TO EXCHANGE");
	}

	/**
	 * @return :  day-schedule-place UUID
	 */
	@Operation(summary = "[인증] 일정 장소 수정 API (업데이트)")
	@ApiResponse(description = "장소 메모 수정 성공 시, ID 반환")
	@PatchMapping("/{travel_plan_id}/day-schedules/{day_schedule_id}/day-schedule-places/{day_schedule_place_id}")
	public ResponseEntity<DaySchedulePlaceResponseDto> updatePlaceMemoInDayScheduleApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") @UUIDFormat String travelPlanUUID,
		@PathVariable("day_schedule_id") @UUIDFormat String dayScheduleUUID,
		@PathVariable("day_schedule_place_id") @UUIDFormat String daySchedulePlaceUUID,
		@Valid @RequestBody DayScheduleUpdatePlaceRequestDto request) {

		DaySchedulePlaceResponseDto response = travelPlanService.updatePlaceInDaySchedule(
			new DayScheduleAccessOption(user, travelPlanUUID, dayScheduleUUID),
			daySchedulePlaceUUID, request.getMemo());

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 일정 장소 삭제 API")
	@DeleteMapping("/{travel_plan_id}/day-schedules/{day_schedule_id}/day-schedule-places/{day_schedule_place_id}")
	public ResponseEntity<String> deletePlaceInDayScheduleApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("travel_plan_id") @UUIDFormat String travelPlanUUID,
		@PathVariable("day_schedule_id") @UUIDFormat String dayScheduleUUID,
		@PathVariable("day_schedule_place_id") @UUIDFormat String daySchedulePlaceUUID) {
		travelPlanService.deletePlaceInDaySchedule(
			new DayScheduleAccessOption(user, travelPlanUUID, dayScheduleUUID),
			daySchedulePlaceUUID);

		return ResponseEntity.ok("SUCCESS TO DELETE");
	}
}
