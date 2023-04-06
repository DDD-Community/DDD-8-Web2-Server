package ddd.caffeine.ratrip.module.place.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.PlaceException;
import ddd.caffeine.ratrip.module.place.application.dto.PlaceDetailDto;
import ddd.caffeine.ratrip.module.place.application.dto.RecommendByLocationDto;
import ddd.caffeine.ratrip.module.place.application.dto.SearchPlaceDto;
import ddd.caffeine.ratrip.module.place.domain.Address;
import ddd.caffeine.ratrip.module.place.domain.Blog;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Location;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceRepository;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.RecommendByLocationDao;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.KakaoPlaceDetail;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.KakaoPlaceMeta;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.PlaceSearchResponseDto;
import ddd.caffeine.ratrip.module.place.presentation.dto.response.RecommendByLocationResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {
	static final long CACHE_EXPIRE_TIME = 60 * 60 * 24 * 7 * 1000L; // 7일
	private final PlaceFeignService placeFeignService;
	private final PlaceRepository placeRepository;

	private final RedisTemplate<String, Object> redisTemplate;

	@Transactional(readOnly = true)
	public PlaceSearchResponseDto searchPlaces(SearchPlaceDto request) {
		KakaoPlaceMeta kakaoPlaceMeta = placeFeignService.findPlacesByKeywordAndLocationFromKakao(request);

		return kakaoPlaceMeta.mapByPlaceSearchResponseDto();
	}

	public Place getPlaceDetail(PlaceDetailDto request) {
		// 캐쉬에 있는지 확인
		String cacheKey = "place:" + request.getKakaoId();
		Place cache = (Place)redisTemplate.opsForValue().get(cacheKey);

		if (cache == null) {
			// 캐쉬에 없으면 API 콜 및 DB에 저장
			return saveAndCachePlace(request, cacheKey);
		}

		increaseViewCount(cache.getId());
		return cache;
	}

	public RecommendByLocationResponseDto recommendByLocation(RecommendByLocationDto request, Pageable pageable) {
		Region region = convertLocationToRegion(request.getLongitude(), request.getLatitude());
		Slice<RecommendByLocationDao> places = placeRepository.findByRegion(region, pageable);

		return RecommendByLocationResponseDto.of(places.getContent(), places.hasNext());
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

	public Region convertLocationToRegion(Double longitude, Double latitude) {
		return placeFeignService.convertLocationToRegionFromKakao(longitude, latitude);
	}

	private Place updatePlace(String originPlaceKakaoId, Place newPlace) {
		// 기존에 장소가 있으면 update 후 조회수 증가, 없으면 save
		Place place = placeRepository.findByKakaoId(originPlaceKakaoId)
			.map(originPlace -> {
				originPlace.updateToNewData(newPlace);
				originPlace.increaseViewCount();
				return originPlace;
			})
			.orElseGet(() -> {
				placeRepository.save(newPlace);
				return newPlace;
			});

		return place;
	}

	private void increaseViewCount(Long id) {
		Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE_EXCEPTION));
		place.increaseViewCount();
	}

	private Place saveAndCachePlace(PlaceDetailDto request, String cacheKey) {
		// API 콜
		Place updatedPlace = findPlaceFromFeign(request.getName(), request.getAddress());

		// DB에 없데이트
		updatedPlace = updatePlace(request.getKakaoId(), updatedPlace);

		// 캐시에 저장
		redisTemplate.opsForValue().set(cacheKey, updatedPlace, CACHE_EXPIRE_TIME, TimeUnit.MILLISECONDS);

		return updatedPlace;
	}

	private Place findPlaceFromFeign(String name, String address) {
		final String QUERY = address + " " + name;

		KakaoPlaceDetail kakaoPlaceDetail = placeFeignService.findPlaceDetailFromKakao(QUERY).getOne();
		String imageLink = placeFeignService.findImageFromNaver(QUERY).toImageLink();
		List<Blog> blogs = placeFeignService.findBlogsFromNaver(QUERY).toBlogs();

		return Place.of(kakaoPlaceDetail.getId(), kakaoPlaceDetail.getPlaceName(), kakaoPlaceDetail.getPlaceUrl(),
			kakaoPlaceDetail.getPhone(), Category.codeToCategory(kakaoPlaceDetail.getCategoryGroupCode()),
			Location.of(kakaoPlaceDetail.getX(), kakaoPlaceDetail.getY()),
			Address.of(kakaoPlaceDetail.getAddressName()), imageLink, blogs);
	}
}
