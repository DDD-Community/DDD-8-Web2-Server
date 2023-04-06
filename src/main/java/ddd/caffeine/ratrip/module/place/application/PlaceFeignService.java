package ddd.caffeine.ratrip.module.place.application;

import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.common.secret.SecretKeyManager;
import ddd.caffeine.ratrip.common.util.HttpHeaderUtils;
import ddd.caffeine.ratrip.module.place.application.dto.SearchPlaceDto;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.feign.kakao.KakaoFeignClient;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceModel;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.KakaoRegionResponse;
import ddd.caffeine.ratrip.module.place.feign.naver.NaverFeignClient;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignImageModel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceFeignService {
	private final SecretKeyManager secretKeyManager;
	private final KakaoFeignClient kakaoFeignClient;
	private final NaverFeignClient naverFeignClient;

	public FeignPlaceModel findPlacesByKeywordAndCoordinate(SearchPlaceDto request) {
		final String KAKAO_API_KEY = secretKeyManager.getKakaoRestApiKey();
		final String KAKAO_REQUEST_HEADER = "KakaoAK " + KAKAO_API_KEY;

		String latitude = String.valueOf(request.getLatitude());
		String longitude = String.valueOf(request.getLongitude());

		return kakaoFeignClient.findPlacesByKeywordInRadius(
			KAKAO_REQUEST_HEADER, request.getKeyword(), latitude, longitude, request.getPage());
	}

	public FeignPlaceModel findPlaceDetailByNameAndAddress(String name, String address) {
		final String KAKAO_API_KEY = secretKeyManager.getKakaoRestApiKey();

		final String KAKAO_REQUEST_HEADER = "KakaoAK " + KAKAO_API_KEY;
		final String keyword = address + " " + name;
		final int DATA_COUNT = 1;

		return kakaoFeignClient.findPlaceByKeyword(KAKAO_REQUEST_HEADER, keyword,
			DATA_COUNT);
	}

	public FeignImageModel findImageModelFromKakao(String keyword) {
		final int DATA_COUNT = 1;
		final String SORT_TYPE = "sim";
		final String SIZE_FILTER = "medium";
		final String NAVER_CLIENT_KEY = secretKeyManager.getNaverClientKey();
		final String NAVER_SECRET_KEY = secretKeyManager.getNaverSecretKey();

		FeignImageModel imageModel = naverFeignClient.readImageModelByPlaceName(
			NAVER_CLIENT_KEY, NAVER_SECRET_KEY, keyword, DATA_COUNT, SORT_TYPE, SIZE_FILTER
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
