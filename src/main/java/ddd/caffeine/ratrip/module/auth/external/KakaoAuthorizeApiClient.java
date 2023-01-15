package ddd.caffeine.ratrip.module.auth.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ddd.caffeine.ratrip.module.auth.external.dto.request.KakaoBearerTokenRequest;
import ddd.caffeine.ratrip.module.auth.external.dto.response.KakaoBearerTokenResponse;

@FeignClient(name = "kakaoAuthorizeApiClient", url = "https://kauth.kakao.com")
public interface KakaoAuthorizeApiClient {
	@PostMapping(value = "/oauth/token", consumes = "application/x-www-form-urlencoded")
	KakaoBearerTokenResponse getBearerToken(@RequestBody KakaoBearerTokenRequest kakaoBearerTokenRequest);
}
