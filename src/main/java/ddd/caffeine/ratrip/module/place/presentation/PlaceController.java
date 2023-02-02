package ddd.caffeine.ratrip.module.place.presentation;

import java.util.List;

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
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceDetailResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceInRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceSaveByThirdPartyRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceSaveThirdPartyResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceSearchRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlaceByRegionRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlaceResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkPlacesByRegionResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

	@Operation(summary = "[인증] 장소 키워드 검색 API")
	@GetMapping("search")
	public ResponseEntity<PlaceSearchResponseDto> callPlaceSearchApi(
		@Valid @ModelAttribute PlaceSearchRequestDto request) {

		PlaceSearchResponseDto response = placeService.searchPlaces(request.mapByThirdPartySearchOption());
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 카카오 정보를 통한 장소 저장 및 업데이트 API")
	@PostMapping
	public ResponseEntity<PlaceSaveThirdPartyResponseDto> callSavePlaceByThirdPartyData(
		@Valid @RequestBody PlaceSaveByThirdPartyRequestDto request) {

		PlaceSaveThirdPartyResponseDto response = placeService.savePlaceByThirdPartyData(
			request.mapByThirdPartyDetailSearchOption());
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 장소 기본키(UUID)로 장소 상세 읽기 API")
	@GetMapping("/{id}")
	public ResponseEntity<PlaceDetailResponseDto> callPlaceDetailsApiByUUID(
		@PathVariable @UUIDFormat String id) {

		PlaceDetailResponseDto response = placeService.readPlaceDetailsByUUID(id);
		return ResponseEntity.ok(response);
	}

	/**
	 * default page = 0
	 * Todo : default size 정하기.
	 */
	@Operation(summary = "[인증] 지역 별 장소 불러오기 (default 옵션 : 인기순정렬, 데이터 5개씩, 내림차순) "
		+ "!!! 옵션을 파라미터에 명시하면 덮어쓰기 가능")
	@GetMapping(value = "regions")
	public ResponseEntity<PlaceInRegionResponseDto> callPlacesInRegionsApi(
		@RequestParam(name = "region", required = false, defaultValue = "전국") List<String> regions,
		@PageableDefault(
			size = 5, sort = "popular", direction = Sort.Direction.DESC) Pageable pageable) {
		PlaceInRegionResponseDto response = placeService.readPlacesInRegions(regions, pageable);
		return ResponseEntity.ok(response);
	}

	//TODO - 인자를 Enum 타입으로 받는 법 알아보기
	@Operation(summary = "[인증] 카테고리별 북마크 리스트 페이지네이션 조회")
	@GetMapping("/bookmarks")
	public ResponseEntity<BookmarkPlaceResponseDto> callReadBookmarksApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@RequestParam(name = "category", defaultValue = "모든 카테고리", required = false) List<String> categories,
		@PageableDefault(size = 7, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		BookmarkPlaceResponseDto response = placeService.readBookmarks(user, categories, pageable);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 특정 장소에 대한 북마크 조회")
	@GetMapping("/{place_id}/bookmarks")
	public ResponseEntity<BookmarkResponseDto> callReadBookmarkOfPlaceApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable(name = "place_id") @UUIDFormat String placeUUID) {
		BookmarkResponseDto response = placeService.readBookmark(user, placeUUID);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 북마크 생성")
	@PostMapping("/{place_id}/bookmarks")
	public ResponseEntity<BookmarkResponseDto> callCreateBookmarkApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable(name = "place_id") @UUIDFormat String placeUUID) {
		BookmarkResponseDto response = placeService.createBookmark(user, placeUUID);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 북마크 상태 변경")
	@PatchMapping("/{place_id}/bookmarks")
	public ResponseEntity<BookmarkResponseDto> callChangeBookmarkStateApi(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@PathVariable(name = "place_id") @UUIDFormat String placeUUID) {
		BookmarkResponseDto response = placeService.changeBookmarkState(user, placeUUID);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[인증] 유저가 선택한 지역 기반 북마크 리스트 최대 4개 조회")
	@GetMapping("/bookmarks/region")
	public ResponseEntity<BookmarkPlacesByRegionResponseDto> getBookmarkPlacesByRegion(
		@Parameter(hidden = true) @AuthenticationPrincipal User user,
		@Valid @ModelAttribute BookmarkPlaceByRegionRequestDto request) {

		return ResponseEntity.ok(placeService.getBookmarkPlacesByRegion(user, request.toServiceDto()));
	}
}
