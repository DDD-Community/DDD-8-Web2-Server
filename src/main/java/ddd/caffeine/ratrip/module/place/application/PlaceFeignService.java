package ddd.caffeine.ratrip.module.place.application;

import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.common.secret.SecretKeyManager;
import ddd.caffeine.ratrip.module.place.application.dto.SearchPlaceDto;
import ddd.caffeine.ratrip.module.place.feign.kakao.KakaoFeignClient;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceModel;
import ddd.caffeine.ratrip.module.place.feign.naver.NaverFeignClient;
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
}
