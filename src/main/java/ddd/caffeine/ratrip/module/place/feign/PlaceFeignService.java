package ddd.caffeine.ratrip.module.place.feign;

import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.common.secret.SecretKeyManager;
import ddd.caffeine.ratrip.module.place.feign.kakao.KakaoFeignClient;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.PlaceKakaoModel;
import ddd.caffeine.ratrip.module.place.feign.naver.NaverFeignClient;
import ddd.caffeine.ratrip.module.place.feign.naver.model.ImageNaverModel;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartySearchOption;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceFeignService {
	private final SecretKeyManager secretKeyManager;
	private final KakaoFeignClient kakaoFeignClient;
	private final NaverFeignClient naverFeignClient;

	/**
	 * 주소와 장소이름을 토대로 하나의 장소를 읽어옵니다.
	 */
	public PlaceKakaoModel readPlacesByAddressAndPlaceName(String address, String placeName) {
		final String KAKAO_API_KEY = secretKeyManager.getKakaoRestApiKey();

		final String KAKAO_REQUEST_HEADER = "KakaoAK " + KAKAO_API_KEY;
		String keyword = address + " " + placeName;
		final int DATA_COUNT = 1;

		return kakaoFeignClient.readPlaceByKeyword(KAKAO_REQUEST_HEADER, keyword,
			DATA_COUNT);
	}

	/**
	 * keyword 를 토대로 주변 5km 내의 장소들을 읽어옵니다.
	 *
	 * @Todo : 반경 몇 km 까지 할 것인지 프론트 개발자님들과 상의.
	 */
	public PlaceKakaoModel readPlacesByKeywordAndCoordinate(
		ThirdPartySearchOption option) {
		final String KAKAO_API_KEY = secretKeyManager.getKakaoRestApiKey();

		final String KAKAO_REQUEST_HEADER = "KakaoAK " + KAKAO_API_KEY;
		final int PLACE_RADIUS = 5000;

		return kakaoFeignClient.readPlacesByKeywordInRadius(
			KAKAO_REQUEST_HEADER, option.getKeyword(), option.getLatitude(), option.getLongitude(), PLACE_RADIUS,
			option.getPage());
	}

	/**
	 * keyword 의 사진 데이터를 읽어 옵니다.
	 */
	public ImageNaverModel readImageModel(String keyword) {
		final int DATA_COUNT = 1;
		final String SORT_TYPE = "sim";
		final String NAVER_CLIENT_KEY = secretKeyManager.getNaverClientKey();
		final String NAVER_SECRET_KEY = secretKeyManager.getNaverSecretKey();

		ImageNaverModel imageModel = naverFeignClient.readImageModelByPlaceName(
			NAVER_CLIENT_KEY, NAVER_SECRET_KEY, keyword, DATA_COUNT, SORT_TYPE
		);

		return imageModel;
	}

}
