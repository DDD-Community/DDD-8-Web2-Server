package ddd.caffeine.ratrip.module.bookmark.presentation;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.bookmark.application.BookmarkService;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
	private final BookmarkService bookmarkService;

	@PostMapping("/{placeId}")
	public ResponseEntity<UUID> addBookmark(@PathVariable UUID placeId, @AuthenticationPrincipal User user) {
		return ResponseEntity.ok(bookmarkService.addBookmark(placeId, user));
	}

	@DeleteMapping("/{placeId}")
	public ResponseEntity<UUID> deleteBookmark(@PathVariable UUID placeId, @AuthenticationPrincipal User user) {
		return ResponseEntity.ok(bookmarkService.deleteBookmark(placeId, user));
	}

	//북마크 조회 기능도 구현해야함
	//헤더 스웨거 추가
}
