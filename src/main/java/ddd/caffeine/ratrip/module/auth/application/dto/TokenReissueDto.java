package ddd.caffeine.ratrip.module.auth.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TokenReissueDto {
	private String accessToken;
	private String refreshToken;

	public static TokenReissueDto of(final String accessToken, final String refreshToken) {
		return new TokenReissueDto(accessToken, refreshToken);
	}
}
