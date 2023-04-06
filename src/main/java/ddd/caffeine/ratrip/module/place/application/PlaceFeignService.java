package ddd.caffeine.ratrip.module.place.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.secret.SecretKeyManager;
import ddd.caffeine.ratrip.common.util.HttpHeaderUtils;
import ddd.caffeine.ratrip.module.place.application.dto.SearchPlaceDto;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.feign.kakao.KakaoFeignClient;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.KakaoPlaceMeta;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.KakaoRegionResponse;
import ddd.caffeine.ratrip.module.place.feign.naver.NaverFeignClient;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignBlogModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignImageModel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceFeignService {
	private final SecretKeyManager secretKeyManager;
	private final KakaoFeignClient kakaoFeignClient;
	private final NaverFeignClient naverFeignClient;

	public KakaoPlaceMeta findPlacesByKeywordAndCoordinate(SearchPlaceDto request) {
		final String KAKAO_API_KEY = secretKeyManager.getKakaoRestApiKey();
		final String KAKAO_REQUEST_HEADER = "KakaoAK " + KAKAO_API_KEY;
		final Integer SIZE = 15;

		String latitude = String.valueOf(request.getLatitude());
		String longitude = String.valueOf(request.getLongitude());

		return kakaoFeignClient.findPlacesByKeywordInRadius(
			KAKAO_REQUEST_HEADER, request.getKeyword(), latitude, longitude, request.getPage(), SIZE);
	}

	public KakaoPlaceMeta findPlaceDetailFromKakao(String query) {
		final String KAKAO_API_KEY = secretKeyManager.getKakaoRestApiKey();

		final String KAKAO_REQUEST_HEADER = "KakaoAK " + KAKAO_API_KEY;
		final int SIZE = 1;

		return kakaoFeignClient.findPlaceByKeyword(KAKAO_REQUEST_HEADER, query,
			SIZE);
	}

	public FeignBlogModel findBlogsFromNaver(String query) {
		final int DISPLAY = 3;
		final String SORT = "sim";
		final String NAVER_CLIENT_KEY = secretKeyManager.getNaverClientKey();
		final String NAVER_SECRET_KEY = secretKeyManager.getNaverSecretKey();

		return naverFeignClient.readBlogModelsByKeyword(NAVER_CLIENT_KEY, NAVER_SECRET_KEY, query, DISPLAY, SORT);
	}

	public FeignImageModel findImageFromNaver(String query) {
		final int DATA_COUNT = 1;
		final String SORT_TYPE = "sim";
		final String SIZE_FILTER = "medium";
		final String NAVER_CLIENT_KEY = secretKeyManager.getNaverClientKey();
		final String NAVER_SECRET_KEY = secretKeyManager.getNaverSecretKey();

		FeignImageModel imageModel = naverFeignClient.readImageModelByPlaceName(
			NAVER_CLIENT_KEY, NAVER_SECRET_KEY, query, DATA_COUNT, SORT_TYPE, SIZE_FILTER
		);

		return imageModel;
	}

	public Region convertLongituteAndLatitudeToRegion(final double longitude, final double latitude) {
		final String KAKAO_REQUEST_HEADER = HttpHeaderUtils.concatWithKakaoAKPrefix(
			secretKeyManager.getKakaoRestApiKey());

		KakaoRegionResponse kakaoRegionResponse = kakaoFeignClient.getRegion(KAKAO_REQUEST_HEADER, longitude,
			latitude);

		return kakaoRegionResponse.getDocuments().get(0).getRegion_1depth_name();
	}
}
