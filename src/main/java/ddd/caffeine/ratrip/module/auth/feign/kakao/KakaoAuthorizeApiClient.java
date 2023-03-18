package ddd.caffeine.ratrip.module.auth.feign.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ddd.caffeine.ratrip.module.auth.feign.kakao.model.KakaoBearerTokenRequest;
import ddd.caffeine.ratrip.module.auth.feign.kakao.model.KakaoBearerTokenResponse;

@FeignClient(name = "kakaoAuthorizeApiClient", url = "https://kauth.kakao.com")
public interface KakaoAuthorizeApiClient {
	@PostMapping(value = "/oauth/token", consumes = "application/x-www-form-urlencoded")
	KakaoBearerTokenResponse getBearerToken(@RequestBody final KakaoBearerTokenRequest kakaoBearerTokenRequest);
}
