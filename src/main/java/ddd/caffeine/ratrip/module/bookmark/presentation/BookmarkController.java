package ddd.caffeine.ratrip.module.bookmark.presentation;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.bookmark.application.BookmarkService;
import ddd.caffeine.ratrip.module.bookmark.presentation.dto.request.RecommendByBookmarkAndLocationRequestDto;
import ddd.caffeine.ratrip.module.bookmark.presentation.dto.response.BookmarksByCategoryResponseDto;
import ddd.caffeine.ratrip.module.bookmark.presentation.dto.response.RecommendByBookmarkAndLocationResponseDto;
import ddd.caffeine.ratrip.module.bookmark.presentation.dto.response.RecommendByBookmarkAndRegionResponseDto;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Region;
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
	@PostMapping("/{place_kakao_id}")
	public ResponseEntity<String> addBookmark(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("place_kakao_id") String placeKakaoId) {
		bookmarkService.createBookmark(user, placeKakaoId);
		return ResponseEntity.ok("Bookmark Create Success");
	}

	@Operation(summary = "[인증] 특정 장소 북마크 여부 조회")
	@GetMapping("/{place_kakao_id}")
	public ResponseEntity<Boolean> whetherBookmark(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable("place_kakao_id") String placeKakaoId) {

		return ResponseEntity.ok(bookmarkService.whetherBookmark(user, placeKakaoId));
	}

	@Operation(summary = "[인증] 카테고리별 북마크 페이지네이션 조회")
	@GetMapping()
	public ResponseEntity<BookmarksByCategoryResponseDto> getBookmarksByCategory(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@RequestParam(name = "category") Category category,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

		return ResponseEntity.ok(bookmarkService.getBookmarksByCategory(user, category, pageable));
	}

	@Operation(summary = "[인증] 유저가 여행 계획 생성시 선택한 지역 내에서 북마크한 장소 추천 페이지네이션 조회")
	@GetMapping("/recommendation/region")
	public ResponseEntity<RecommendByBookmarkAndRegionResponseDto> recommendByBookmarkAndRegion(
		@Parameter(hidden = true) @AuthenticationPrincipal User user, @RequestParam Region region,
		@PageableDefault(size = 20) Pageable pageable) {

		return ResponseEntity.ok(bookmarkService.recommendByBookmarkAndRegion(user, region, pageable));
	}

	@Operation(summary = "[인증] 유저가 위치한 현재 지역 내에서 북마크한 추천 페이지네이션 조회")
	@GetMapping("/recommendation/location")
	public ResponseEntity<RecommendByBookmarkAndLocationResponseDto> recommendByBookmarkAndLocation(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @ModelAttribute RecommendByBookmarkAndLocationRequestDto request,
		@PageableDefault(size = 20) Pageable pageable) {

		return ResponseEntity.ok(
			bookmarkService.recommendByBookmarkAndLocation(user, request.toServiceDto(), pageable));
	}
}
