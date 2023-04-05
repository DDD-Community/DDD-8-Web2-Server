package ddd.caffeine.ratrip.module.place.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.PlaceException;
import ddd.caffeine.ratrip.module.place.application.dto.PlaceDetailDto;
import ddd.caffeine.ratrip.module.place.application.dto.SearchPlaceDto;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceRepository;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignImageModel;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceDetailResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {
	static final long CACHE_EXPIRE_TIME = 60 * 60 * 24 * 7; // 7일
	private final PlaceFeignService placeFeignService;
	private final PlaceRepository placeRepository;

	private final RedisTemplate<String, Object> redisTemplate;

	@Transactional(readOnly = true)
	public PlaceSearchResponseDto searchPlaces(SearchPlaceDto request) {
		FeignPlaceModel feignPlaceModel = placeFeignService.findPlacesByKeywordAndCoordinate(request);

		return feignPlaceModel.mapByPlaceSearchResponseDto();
	}

	public PlaceDetailResponseDto getPlaceDetail(User user, PlaceDetailDto request) {
		// 캐쉬에 있는지 확인
		String cacheKey = "place:" + request.getKakaoId();
		PlaceDetailResponseDto cache = (PlaceDetailResponseDto)redisTemplate.opsForValue().get(cacheKey);

		if (cache == null) {
			// 캐쉬에 없으면 API 콜 및 DB에 저장
			return saveAndCachePlaceDetail(request, cacheKey);
		}

		increaseViewCount(cache.getId());
		return cache;
	}

	private void increaseViewCount(Long id) {
		Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE_EXCEPTION));
		place.increaseViewCount();
	}

	private PlaceDetailResponseDto saveAndCachePlaceDetail(PlaceDetailDto request, String cacheKey) {
		// API 콜
		Place place = findPlaceDetailFromKakao(request.getName(), request.getAddress());

		// DB에 없데이트
		place = updatePlace(request, place);

		// 캐시에 저장
		PlaceDetailResponseDto response = PlaceDetailResponseDto.of(place);
		redisTemplate.opsForValue().set(cacheKey, response, CACHE_EXPIRE_TIME, TimeUnit.MILLISECONDS);

		return response;
	}

	public void increaseBookmarkCount(Place place) {
		place.increaseBookmarkCount();
	}

	public Place validateExistPlace(Long placeId) {
		Place place = (Place)redisTemplate.opsForValue().get("place:" + placeId);

		if (place == null) {
			place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE_EXCEPTION));
		}

		return place;
	}

	private Place updatePlace(PlaceDetailDto request, Place place) {
		Place originPlace = placeRepository.findByKakaoId(request.getKakaoId());

		if (originPlace == null) {
			return placeRepository.save(place);
		}

		originPlace.update(place);
		originPlace.increaseViewCount();

		return originPlace;
	}

	private Place findPlaceDetailFromKakao(String name, String address) {
		FeignPlaceModel feignPlaceModel = placeFeignService.findPlaceDetailByNameAndAddress(name, address);
		Place place = feignPlaceModel.mapByPlaceEntity();
		setImageLinkInPlace(place, place.getName());
		//setBlogsInPlace(place, place.getName());

		return place;
	}

	private void setImageLinkInPlace(Place place, String keyword) {
		final int DATA_INDEX = 0;

		FeignImageModel imageModel = placeFeignService.findImageModelFromKakao(keyword);
		place.setImageLink(imageModel.readImageLinkByIndex(DATA_INDEX));
	}
}
