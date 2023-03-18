package ddd.caffeine.ratrip.module.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.secret.SecretKeyManager;
import ddd.caffeine.ratrip.common.util.HttpHeaderUtils;
import ddd.caffeine.ratrip.module.auth.external.kakao.KakaoAuthorizeApiClient;
import ddd.caffeine.ratrip.module.auth.external.kakao.KakaoUserApiClient;
import ddd.caffeine.ratrip.module.auth.external.kakao.dto.request.KakaoBearerTokenRequest;
import ddd.caffeine.ratrip.module.auth.external.kakao.dto.response.KakaoBearerTokenResponse;
import ddd.caffeine.ratrip.module.auth.external.kakao.dto.response.KakaoProfile;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoAuthService {
	private final SecretKeyManager secretKeyManager;
	private final KakaoAuthorizeApiClient kakaoAuthorizeApiClient;
	private final KakaoUserApiClient kakaoUserApiClient;

	public KakaoProfile getKakaoProfile(final String authorizationCode) {
		String accessToken = getKakaoAccessToken(authorizationCode);

		return kakaoUserApiClient.getKakaoProfile(HttpHeaderUtils.concatWithBearerPrefix(accessToken));
	}

	public String getKakaoAccessToken(final String authorizationCode) {
		final String KAKAO_API_KEY = secretKeyManager.getKakaoRestApiKey();
		final String KAKAO_REDIRECT_URI = secretKeyManager.getKakaoRedirectUri();

		KakaoBearerTokenResponse kakaoBearerTokenResponse = kakaoAuthorizeApiClient.getBearerToken(
			KakaoBearerTokenRequest.of(KAKAO_API_KEY, KAKAO_REDIRECT_URI, authorizationCode));

		return kakaoBearerTokenResponse.getAccessToken();
	}
}