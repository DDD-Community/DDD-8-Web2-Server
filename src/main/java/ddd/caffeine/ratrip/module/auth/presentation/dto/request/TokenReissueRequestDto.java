package ddd.caffeine.ratrip.module.auth.presentation.dto.request;

import javax.validation.constraints.NotBlank;

import ddd.caffeine.ratrip.module.auth.application.dto.TokenReissueDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenReissueRequestDto {
	@Schema(description = "엑세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJVU0VSX0lEIjoiMTFlZDdjMmUtZGZlZi1hZWFkLTkxM2ItZWJkOWE1N2YwMjE3IiwiZXhwIjoxNjcxMDc5NTAwfQ.pEDYlZZwAykx1wpNNRK8scnaL1SGRTTlJZ4EKC--ja5ZBg8Vz4LiyFEvSy79W1-k9PJuRKY-VCnrG8KzO4IRrQ")
	@NotBlank(message = "Access token must not be blank")
	private String accessToken;

	@Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NzE2ODI1MDB9.7mT7V4dmH7YDYJiTipppSRV5w8x18fEZD4mWXakKpxiRgiXfzBK-94658XRq8DJ51dr9tuzV5WScMVoXxs_GlQ")
	@NotBlank(message = "Refresh token must not be blank")
	private String refreshToken;

	public TokenReissueDto toServiceDto() {
		return TokenReissueDto.of(accessToken, refreshToken);
	}
}