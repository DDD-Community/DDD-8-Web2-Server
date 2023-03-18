package ddd.caffeine.ratrip.module.user.application.presentation.dto.response;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignOutResponseDto {
	private final UUID id;

	public static SignOutResponseDto of(final UUID id) {
		return new SignOutResponseDto(id);
	}
}
