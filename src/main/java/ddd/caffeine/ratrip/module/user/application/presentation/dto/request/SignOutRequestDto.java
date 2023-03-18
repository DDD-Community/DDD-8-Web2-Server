package ddd.caffeine.ratrip.module.user.application.presentation.dto.request;

import javax.validation.constraints.NotBlank;

import ddd.caffeine.ratrip.module.auth.application.dto.SignOutDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignOutRequestDto {
	@Schema(description = "엑세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJVU0VSX0lEIjoiMTFlZDdjMmUtZGZlZi1hZWFkLTkxM2ItZWJkOWE1N2YwMjE3IiwiZXhwIjoxNjcxMDc5NTAwfQ.pEDYlZZwAykx1wpNNRK8scnaL1SGRTTlJZ4EKC--ja5ZBg8Vz4LiyFEvSy79W1-k9PJuRKY-VCnrG8KzO4IRrQ")
	@NotBlank(message = "Access Token must not be blank")
	private String accessToken;

	@Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NzE2ODI1MDB9.7mT7V4dmH7YDYJiTipppSRV5w8x18fEZD4mWXakKpxiRgiXfzBK-94658XRq8DJ51dr9tuzV5WScMVoXxs_GlQ")
	@NotBlank(message = "Token must not be blank")
	private String refreshToken;

	public SignOutDto toServiceDto() {
		return SignOutDto.of(accessToken, refreshToken);
	}
}
