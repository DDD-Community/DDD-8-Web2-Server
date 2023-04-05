package ddd.caffeine.ratrip.module.memo.presentation;

import javax.validation.Valid;

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

import ddd.caffeine.ratrip.module.memo.application.MemoService;
import ddd.caffeine.ratrip.module.memo.presentation.dto.request.ChangeMemoSequenceRequestDto;
import ddd.caffeine.ratrip.module.memo.presentation.dto.request.CreateMemoRequestDto;
import ddd.caffeine.ratrip.module.memo.presentation.dto.request.MemosRequestDto;
import ddd.caffeine.ratrip.module.memo.presentation.dto.request.RecommendationRequestDto;
import ddd.caffeine.ratrip.module.memo.presentation.dto.request.UpdateMemoRequestDto;
import ddd.caffeine.ratrip.module.memo.presentation.dto.response.MemosResponseDto;
import ddd.caffeine.ratrip.module.memo.presentation.dto.response.RecommendationPathResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/memos")
public class MemoController {

	private final MemoService memoService;

	@Operation(summary = "[인증] 하루 일정에 메모 추가")
	@PostMapping()
	public ResponseEntity<String> createMemo(@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @RequestBody CreateMemoRequestDto request) {

		memoService.createMemo(user, request.toServiceDto());
		return ResponseEntity.ok("Create Memo Success");
	}

	@Operation(summary = "[인증] 메모 순서 변경")
	@PatchMapping("/sequence")
	public ResponseEntity<String> changeMemoSequence(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @RequestBody ChangeMemoSequenceRequestDto request) {

		memoService.changeMemoSequence(user, request.toServiceDto());
		return ResponseEntity.ok("Change Memo Sequence Success");
	}

	@Operation(summary = "[인증] 하루 일정의 모든 메모 조회")
	@GetMapping
	public ResponseEntity<MemosResponseDto> getMemos(@Parameter(hidden = true) @AuthenticationPrincipal User user,
		MemosRequestDto request) {

		return ResponseEntity.ok(memoService.getMemos(user, request.toServiceDto()));
	}

	@Operation(summary = "[인증] 메모 수정")
	@PatchMapping("/{memo_id}")
	public ResponseEntity<String> updateMemo(
		@Parameter(hidden = true) @AuthenticationPrincipal User user, @PathVariable("memo_id") Long memoId,
		@Valid @RequestBody UpdateMemoRequestDto request) {

		memoService.updateMemo(user, request.toServiceDto(memoId));
		return ResponseEntity.ok("Update Memo Success");
	}

	@Operation(summary = "[인증] 선택한 메모 기준 경로 추천")
	@GetMapping("/{memo_id}/recommendation-path")
	public ResponseEntity<RecommendationPathResponseDto> getRecommendationPath(
		@Parameter(hidden = true) @AuthenticationPrincipal User user, @PathVariable("memo_id") Long memoId,
		@Valid @RequestBody RecommendationRequestDto request) {

		return ResponseEntity.ok(memoService.getRecommendationPath(user, request.toServiceDto(memoId)));
	}
}
