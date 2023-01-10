package ddd.caffeine.ratrip.module.auth.application;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.module.auth.external.dto.response.KakaoProfile;
import ddd.caffeine.ratrip.module.auth.presentation.dto.response.SignInResponseDto;
import ddd.caffeine.ratrip.module.auth.presentation.dto.response.TokenResponseDto;
import ddd.caffeine.ratrip.module.user.application.UserService;
import ddd.caffeine.ratrip.module.user.application.dto.SignUpUserDto;
import ddd.caffeine.ratrip.module.user.domain.UserSocialType;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final UserService userService;
	private final TokenService tokenService;
	private final KakaoAuthService kakaoAuthService;

	public SignInResponseDto signInWithKakao(String authorizationCode) {
		KakaoProfile kakaoProfile = kakaoAuthService.getKakaoProfile(authorizationCode);
		UUID userId = userService.findUserIdBySocialIdAndSocialType(
			SignUpUserDto.of(kakaoProfile, UserSocialType.KAKAO));
		TokenResponseDto tokenResponseDto = tokenService.createJwtToken(userId);

		return SignInResponseDto.of(userId, tokenResponseDto);
	}
}
