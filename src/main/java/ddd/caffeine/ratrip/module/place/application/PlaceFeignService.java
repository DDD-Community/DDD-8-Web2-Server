package ddd.caffeine.ratrip.module.place.application;

import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.common.secret.SecretKeyManager;
import ddd.caffeine.ratrip.common.util.HttpHeaderUtils;
import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartySearchOption;
import ddd.caffeine.ratrip.module.place.feign.kakao.KakaoFeignClient;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceModel;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.KakaoRegionResponse;
import ddd.caffeine.ratrip.module.place.feign.naver.NaverFeignClient;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignBlogModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignImageModel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceFeignService {
	private final SecretKeyManager secretKeyManager;
	private final KakaoFeignClient kakaoFeignClient;
	private final NaverFeignClient naverFeignClient;

	public Region convertLongituteAndLatitudeToRegion(final double longitude, final double latitude) {
		final String KAKAO_REQUEST_HEADER = HttpHeaderUtils.concatWithKakaoAKPrefix(
			secretKeyManager.getKakaoRestApiKey());

		KakaoRegionResponse kakaoRegionResponse = kakaoFeignClient.getRegion(KAKAO_REQUEST_HEADER, longitude,
			latitude);

		return kakaoRegionResponse.getDocuments().get(0).getRegion_1depth_name();
	}

	/**
	 * 주소와 장소이름을 토대로 하나의 장소를 읽어옵니다.
	 */
	public FeignPlaceModel findPlaceDetailByNameAndAddress(String name, String address) {
		final String KAKAO_API_KEY = secretKeyManager.getKakaoRestApiKey();

		final String KAKAO_REQUEST_HEADER = "KakaoAK " + KAKAO_API_KEY;
		final String keyword = address + " " + name;
		final int DATA_COUNT = 1;

		return kakaoFeignClient.readPlaceByKeyword(KAKAO_REQUEST_HEADER, keyword,
			DATA_COUNT);
	}

	/**
	 * keyword 를 토대로 주변 5km 내의 장소들을 읽어옵니다.
	 */
	public FeignPlaceModel readPlacesByKeywordAndCoordinate(
		ThirdPartySearchOption option) {
		final String KAKAO_API_KEY = secretKeyManager.getKakaoRestApiKey();

		final String KAKAO_REQUEST_HEADER = "KakaoAK " + KAKAO_API_KEY;
		final int PLACE_RADIUS = 20000;

		return kakaoFeignClient.readPlacesByKeywordInRadius(
			KAKAO_REQUEST_HEADER, option.getKeyword(), option.readLatitude(), option.readLongitude(), PLACE_RADIUS,
			option.getPage());
	}

	/**
	 * keyword 의 사진 데이터를 읽어 옵니다.
	 */
	public FeignImageModel readImageModel(String keyword) {
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

	public FeignBlogModel readBlogModel(String keyword) {
		final int DATA_COUNT = 3;
		final String SORT_TYPE = "sim";
		final String NAVER_CLIENT_KEY = secretKeyManager.getNaverClientKey();
		final String NAVER_SECRET_KEY = secretKeyManager.getNaverSecretKey();

		FeignBlogModel feignBlogModel = naverFeignClient.readBlogModelsByKeyword(
			NAVER_CLIENT_KEY, NAVER_SECRET_KEY, keyword, DATA_COUNT, SORT_TYPE
		);

		return feignBlogModel;
	}
}
