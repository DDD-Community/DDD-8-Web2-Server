package ddd.caffeine.ratrip.module.place.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.PlaceException;
import ddd.caffeine.ratrip.module.place.application.dto.PlaceDetailDto;
import ddd.caffeine.ratrip.module.place.application.dto.SearchPlaceDto;
import ddd.caffeine.ratrip.module.place.domain.Address;
import ddd.caffeine.ratrip.module.place.domain.Blog;
import ddd.caffeine.ratrip.module.place.domain.Location;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceRepository;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.KakaoPlaceDetail;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.KakaoPlaceMeta;
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
		KakaoPlaceMeta kakaoPlaceMeta = placeFeignService.findPlacesByKeywordAndCoordinate(request);

		return kakaoPlaceMeta.mapByPlaceSearchResponseDto();
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

	public void increaseBookmarkCount(Place place) {
		place.increaseBookmarkCount();
	}

	public Place validateExistPlace(String placeKakaoId) {
		Place place = (Place)redisTemplate.opsForValue().get("place:" + placeKakaoId);

		if (place == null) {
			place = placeRepository.findByKakaoId(placeKakaoId)
				.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE_EXCEPTION));
		}

		return place;
	}

	private Place updatePlace(String originPlaceKakaoId, Place newPlace) {
		// 기존에 장소가 있으면 update, 없으면 save 후 조회수 증가
		Place place = placeRepository.findByKakaoId(originPlaceKakaoId)
			.map(originPlace -> {
				originPlace.updateToNewData(newPlace);
				return originPlace;
			})
			.orElseGet(() -> {
				placeRepository.save(newPlace);
				return newPlace;
			});

		place.increaseViewCount();

		return place;
	}

	private void increaseViewCount(Long id) {
		Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE_EXCEPTION));
		place.increaseViewCount();
	}

	private PlaceDetailResponseDto saveAndCachePlaceDetail(PlaceDetailDto request, String cacheKey) {
		// API 콜
		Place updatedPlace = findPlaceFromFeign(request.getName(), request.getAddress());

		// DB에 없데이트
		updatedPlace = updatePlace(request.getKakaoId(), updatedPlace);

		// 캐시에 저장
		PlaceDetailResponseDto response = PlaceDetailResponseDto.of(updatedPlace);
		redisTemplate.opsForValue().set(cacheKey, response, CACHE_EXPIRE_TIME, TimeUnit.MILLISECONDS);

		return response;
	}

	private Place findPlaceFromFeign(String name, String address) {
		final String QUERY = address + " " + name;

		KakaoPlaceDetail kakaoPlaceDetail = placeFeignService.findPlaceDetailFromKakao(QUERY).getOne();
		String imageLink = placeFeignService.findImageFromNaver(QUERY).toImageLink();
		List<Blog> blogs = placeFeignService.findBlogsFromNaver(QUERY).toBlogs();

		return Place.of(kakaoPlaceDetail.getId(), kakaoPlaceDetail.getPlaceName(), kakaoPlaceDetail.getPlaceUrl(),
			kakaoPlaceDetail.getPhone(), kakaoPlaceDetail.getCategoryGroupCode(),
			Location.of(kakaoPlaceDetail.getX(), kakaoPlaceDetail.getY()),
			Address.of(kakaoPlaceDetail.getAddressName()), imageLink, blogs);
	}
}
