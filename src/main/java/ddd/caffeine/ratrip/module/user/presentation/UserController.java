package ddd.caffeine.ratrip.module.user.presentation;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.user.application.UserService;
import ddd.caffeine.ratrip.module.user.domain.User;
import ddd.caffeine.ratrip.module.user.presentation.dto.request.UpdateUserNameRequestDto;
import ddd.caffeine.ratrip.module.user.presentation.dto.response.UserNameResponseDto;
import ddd.caffeine.ratrip.module.user.presentation.dto.response.UserNameUpdateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@Operation(summary = "회원 이름 조회")
	@GetMapping("/name")
	public ResponseEntity<UserNameResponseDto> findUserName(
		@Parameter(hidden = true) @AuthenticationPrincipal User user) {
		return ResponseEntity.ok(userService.findUserName(user));
	}

	@Operation(summary = "회원 이름 변경")
	@PatchMapping("/name")
	public ResponseEntity<UserNameUpdateResponseDto> updateUserName(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @RequestBody UpdateUserNameRequestDto request) {
		return ResponseEntity.ok(userService.updateName(user, request.toServiceDto()));
	}

}
