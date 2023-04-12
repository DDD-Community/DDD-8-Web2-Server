package ddd.caffeine.ratrip.module.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.module.auth.application.dto.SignOutDto;
import ddd.caffeine.ratrip.module.auth.application.dto.TokenReissueDto;
import ddd.caffeine.ratrip.module.auth.feign.kakao.model.KakaoProfile;
import ddd.caffeine.ratrip.module.auth.presentation.dto.response.SignInResponseDto;
import ddd.caffeine.ratrip.module.auth.presentation.dto.response.SignOutResponseDto;
import ddd.caffeine.ratrip.module.auth.presentation.dto.response.TokenResponseDto;
import ddd.caffeine.ratrip.module.user.application.UserService;
import ddd.caffeine.ratrip.module.user.application.dto.SignUpUserDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import ddd.caffeine.ratrip.module.user.domain.UserSocialType;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final UserService userService;
	private final TokenService tokenService;
	private final KakaoAuthService kakaoAuthService;

	public SignInResponseDto signInWithKakao(final String authorizationCode) {
		KakaoProfile kakaoProfile = kakaoAuthService.getKakaoProfile(authorizationCode);

		User user = userService.findUserIdBySocialIdAndSocialType(
			SignUpUserDto.ofKakao(kakaoProfile, UserSocialType.KAKAO));

		TokenResponseDto tokenResponseDto = tokenService.createJwtToken(user.getId());

		return SignInResponseDto.of(user.getId(), tokenResponseDto);
	}

	public TokenResponseDto reissueToken(final TokenReissueDto request) {
		return tokenService.reissueToken(request);
	}

	public SignOutResponseDto signOut(final SignOutDto request) {
		return tokenService.deleteToken(request);
	}
}
