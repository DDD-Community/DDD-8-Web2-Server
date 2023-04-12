package ddd.caffeine.ratrip.module.auth.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignOutDto {
	private String accessToken;
	private String refreshToken;

	public static SignOutDto of(final String accessToken, final String refreshToken) {
		return new SignOutDto(accessToken, refreshToken);
	}
}