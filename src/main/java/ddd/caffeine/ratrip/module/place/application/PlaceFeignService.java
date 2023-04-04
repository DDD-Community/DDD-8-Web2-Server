package ddd.caffeine.ratrip.module.place.application;

import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.common.secret.SecretKeyManager;
import ddd.caffeine.ratrip.module.place.feign.kakao.KakaoFeignClient;
import ddd.caffeine.ratrip.module.place.feign.naver.NaverFeignClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceFeignService {
	private final SecretKeyManager secretKeyManager;
	private final KakaoFeignClient kakaoFeignClient;
	private final NaverFeignClient naverFeignClient;

}
