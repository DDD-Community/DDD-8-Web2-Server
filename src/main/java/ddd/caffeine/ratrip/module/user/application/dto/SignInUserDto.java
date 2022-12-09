package ddd.caffeine.ratrip.module.user.application.dto;

import ddd.caffeine.ratrip.module.user.domain.UserSocialType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignInUserDto {
	private String socialId;
	private UserSocialType socialType;

	public static SignInUserDto of(String socialId, UserSocialType socialType) {
		return new SignInUserDto(socialId, socialType);
	}
}