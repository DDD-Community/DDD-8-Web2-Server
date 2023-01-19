package ddd.caffeine.ratrip.module.user.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.user.application.UserService;
import ddd.caffeine.ratrip.module.user.domain.User;
import ddd.caffeine.ratrip.module.user.presentation.dto.request.UpdateUserNameRequestDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/health-check")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("health check success");
	}

	@GetMapping("/user/name")
	public ResponseEntity<String> findUserName(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(userService.findUserName(user));
	}

	@PatchMapping("/user/name")
	public ResponseEntity<String> updateUserName(@AuthenticationPrincipal User user,
		@RequestBody UpdateUserNameRequestDto request) {
		return ResponseEntity.ok(userService.updateName(user, request.toServiceDto()));
	}

}
