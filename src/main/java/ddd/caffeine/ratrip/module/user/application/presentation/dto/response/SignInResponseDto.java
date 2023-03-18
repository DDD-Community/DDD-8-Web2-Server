package ddd.caffeine.ratrip.module.user.application.presentation.dto.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignInResponseDto {
	@Schema(description = "유저 ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", type = "UUID")
	private UUID id;
	private TokenResponseDto token;

	public static SignInResponseDto of(final UUID userId, final TokenResponseDto token) {
		return new SignInResponseDto(userId, token);
	}
}
