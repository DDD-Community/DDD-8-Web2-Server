package ddd.caffeine.ratrip.common.secret;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "secret")
public final class SecretKeyManager {
	private final String jwt;
	private final Kakao kakao;
	private final Naver naver;

	public String getKakaoRestApiKey() {
		return kakao.getRestApiKey();
	}

	public String getKakaoRedirectUri() {
		return kakao.getRedirectUri();
	}

	public String getNaverClientKey() {
		return naver.getClientKey();
	}

	public String getNaverSecretKey() {
		return naver.getSecretKey();
	}

	@Getter
	@RequiredArgsConstructor
	public static final class Kakao {
		private final String restApiKey;
		private final String redirectUri;
	}

	@Getter
	@RequiredArgsConstructor
	public static final class Naver {
		private final String clientKey;
		private final String secretKey;
	}
}
