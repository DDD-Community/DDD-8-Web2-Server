package ddd.caffeine.ratrip.module.auth.application.dto;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ddd.caffeine.ratrip.common.exception.domain.AuthException;
import ddd.caffeine.ratrip.module.auth.presentation.dto.request.AppleProfile;
import lombok.Getter;

@Getter
public class SignInWithAppleDto {
	private final String idToken;
	private final String authorizationCode;
	private final AppleProfile user;

	private SignInWithAppleDto(String idToken, String authorizationCode, String user) {
		this.idToken = idToken;
		this.authorizationCode = authorizationCode;
		this.user = castStringToAppleUser(user);
	}

	public static SignInWithAppleDto of(String idToken, String authorizationCode, String user) {
		return new SignInWithAppleDto(idToken, authorizationCode, user);
	}

	private AppleProfile castStringToAppleUser(String user) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(user, AppleProfile.class);
		} catch (JsonProcessingException e) {
			throw new AuthException(APPLE_USER_DATA_CASTING_EXCEPTION);
		}
	}
}
