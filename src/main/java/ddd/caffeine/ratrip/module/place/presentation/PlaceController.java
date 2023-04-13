package ddd.caffeine.ratrip.module.place.presentation;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.presentation.dto.request.PlaceDetailRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.request.PlaceSearchRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.request.RecommendByLocationRequestDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.RecommendByLocationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/places")
public class PlaceController {
	private final PlaceService placeService;

	@Operation(summary = "[인증] 장소 키워드 검색 API")
	@GetMapping("/search")
	public ResponseEntity<PlaceSearchResponseDto> searchPlaces(
		@Valid @ModelAttribute PlaceSearchRequestDto request) {

		return ResponseEntity.ok(placeService.searchPlaces(request.toServiceDto()));
	}

	@Operation(summary = "[인증] 장소 상세 정보 API")
	@PostMapping("")
	public ResponseEntity<Place> getPlaceDetail(@Valid @RequestBody PlaceDetailRequestDto request) {

		return ResponseEntity.ok(placeService.getPlaceDetail(request.toServiceDto()));
	}

	@Operation(summary = "[인증] 유저가 위치한 현재 지역 내의 장소 추천 페이지네이션 조회")
	@GetMapping("/recommendation/location")
	public ResponseEntity<RecommendByLocationResponseDto> recommendByLocation(
		@Valid @ModelAttribute RecommendByLocationRequestDto request,
		@PageableDefault(size = 20) Pageable pageable) {

		return ResponseEntity.ok(
			placeService.recommendByLocation(request.toServiceDto(), pageable));
	}
}
