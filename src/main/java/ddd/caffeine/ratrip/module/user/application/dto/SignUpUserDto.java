package ddd.caffeine.ratrip.module.user.application.dto;

import ddd.caffeine.ratrip.module.auth.feign.kakao.model.KakaoProfile;
import ddd.caffeine.ratrip.module.user.domain.UserSocialType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpUserDto {
	private String socialId;
	private UserSocialType socialType;
	private String name;
	private String email;

	@Builder(access = AccessLevel.PRIVATE)
	private SignUpUserDto(String socialId, UserSocialType socialType, String name, String email) {
		this.socialId = socialId;
		this.socialType = socialType;
		this.name = name;
		this.email = email;
	}

	public static SignUpUserDto ofKakao(final KakaoProfile kakaoProfile, final UserSocialType socialType) {
		return SignUpUserDto.builder()
			.socialId(kakaoProfile.getId())
			.socialType(socialType)
			.name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
			.email(kakaoProfile.getKakaoAccount().getEmail())
			.build();
	}
}