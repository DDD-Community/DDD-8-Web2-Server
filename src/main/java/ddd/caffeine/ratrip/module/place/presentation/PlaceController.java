package ddd.caffeine.ratrip.module.place.presentation;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.common.validator.annotation.UUIDFormat;
import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceInRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlaceResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.detail.PlaceDetailsResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.detail.PlaceSaveByThirdPartyRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.detail.PlaceSaveThirdPartyResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.search.PlaceSearchRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.search.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

/**
 * 장소 API
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/places")
public class PlaceController {
	private final PlaceService placeService;

	@Operation(summary = "장소 키워드 검색 API")
	@GetMapping("search")
	public ResponseEntity<PlaceSearchResponseDto> callPlaceSearchApi(
		@Valid @ModelAttribute PlaceSearchRequestDto request) {

		PlaceSearchResponseDto response = placeService.searchPlaces(request.mapByThirdPartySearchOption());
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "카카오 정보를 통한 장소 저장 및 업데이트 API")
	@PostMapping
	public ResponseEntity<PlaceSaveThirdPartyResponseDto> callSavePlaceByThirdPartyData(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @RequestBody PlaceSaveByThirdPartyRequestDto request) {

		PlaceSaveThirdPartyResponseDto response = placeService.savePlaceByThirdPartyData(
			request.mapByThirdPartyDetailSearchOption(), user);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "장소 기본키(UUID)로 장소 상세 읽기 API")
	@GetMapping("/{id}")
	public ResponseEntity<PlaceDetailsResponseDto> callPlaceDetailsApiByUUID(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable @UUIDFormat String id) {

		PlaceDetailsResponseDto response = placeService.readPlaceDetailsByUUID(id, user);
		return ResponseEntity.ok(response);
	}

	/**
	 * default page = 0
	 * Todo : default size 정하기.
	 */
	@Operation(summary = "지역 별 장소 불러오기 (default 옵션 : 인기순정렬, 데이터 5개씩, 내림차순) "
		+ "!!! 옵션을 파라미터에 명시하면 덮어쓰기 가능")
	@GetMapping(value = "regions")
	public ResponseEntity<PlaceInRegionResponseDto> callPlacesInRegionsApi(
		@RequestParam(name = "region", required = false, defaultValue = "전국") List<String> regions,
		@PageableDefault(
			size = 5, sort = "popular", direction = Sort.Direction.DESC) Pageable pageable) {
		PlaceInRegionResponseDto response = placeService.readPlacesInRegions(regions, pageable);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 북마크 상태 변경")
	@ApiResponse(description = "북마크 등록 성공 시, 북마크 ID & 북마크 상태 반환")
	@PatchMapping("/{place_id}/bookmarks/{bookmark_id}")
	public ResponseEntity<BookmarkResponseDto> callChangeBookmarkStateApi(
		@PathVariable(name = "place_id") @UUIDFormat String placeUUID,
		@PathVariable("bookmark_id") @UUIDFormat String bookmarkUUID) {
		BookmarkResponseDto response = placeService.changeBookmarkState(
			UUID.fromString(placeUUID), UUID.fromString(bookmarkUUID));

		return ResponseEntity.ok(response);
	}

	//TODO - 인자를 Enum 타입으로 받는 법 알아보기
	@Operation(summary = "[인증] 북마크 리스트 조회")
	@GetMapping("/bookmarks")
	public ResponseEntity<BookmarkPlaceResponseDto> getBookmarks(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@RequestParam(name = "category", defaultValue = "모든 카테고리", required = false) List<String> categories,
		@PageableDefault(size = 7, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		BookmarkPlaceResponseDto response = placeService.readBookmarks(user, categories, pageable);
		return ResponseEntity.ok(response);
	}
}
