package ddd.caffeine.ratrip.module.auth.application.dto;

import ddd.caffeine.ratrip.module.user.application.dto.RegisterUserDto;
import ddd.caffeine.ratrip.module.user.domain.UserSocialType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignUpDto {
	private String token;

	private String nickname;

	private String email;

	private UserSocialType socialType;

	public static SignUpDto of(String token, String nickname, String email, UserSocialType socialType) {
		return new SignUpDto(token, nickname, email, socialType);
	}

	public RegisterUserDto createUsedByAppleAuth(String socialId, UserSocialType socialType) {
		return RegisterUserDto.createUsedByAppleAuth(socialId, nickname, email, socialType);
	}
}
