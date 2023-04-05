package ddd.caffeine.ratrip.module.bookmark.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.bookmark.application.BookmarkService;
import ddd.caffeine.ratrip.module.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/bookmarks")
public class BookmarkController {
	private final BookmarkService bookmarkService;

	@Operation(summary = "[인증] 북마크 생성")
	@PostMapping("/{place_id}")
	public ResponseEntity<String> addBookmark(
		@Parameter(hidden = true) @AuthenticationPrincipal User user, @PathVariable("place_id") Long placeId) {
		bookmarkService.createBookmark(user, placeId);
		return ResponseEntity.ok("Bookmark Create Success");
	}

	@Operation(summary = "[인증] 특정 장소 북마크 여부 조회")
	@GetMapping("/{place_id}")
	public ResponseEntity<Boolean> whetherBookmark(
		@Parameter(hidden = true) @AuthenticationPrincipal User user, @PathVariable("place_id") Long placeId) {

		return ResponseEntity.ok(bookmarkService.whetherBookmark(user, placeId));
	}
}
