package ddd.caffeine.ratrip.module.auth.presentation.dto.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import ddd.caffeine.ratrip.module.auth.application.dto.SignInWithAppleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInWithAppleRequestDto {
	@Schema(description = "아이디 토큰", example = "k5AmWU6rE9E6FRu92OP40K_MWkrk8TQJu7xaV8VLCj1zTgAAAYUT_N4T")
	@NotBlank(message = "Token must not be blank")
	@JsonProperty("id_token")
	private String idToken;

	@Schema(description = "인증 코드", example = "cs27903241f4a4cf395ba97456570b048.0.rafsfr.Z6E5z7DOSgRRrZXRmdybDQ")
	@NotBlank(message = "Authorization code must not be blank")
	@JsonProperty("code")
	private String authorizationCode;

	@Schema(description = "사용자 정보", example = "{\"name\":{\"firstName\":\"페인\",\"lastName\":\"카\"},\"email\":\"caffeineratrip@gmail.com\"}")
	//@NotBlank(message = "User must not be blank")
	@JsonProperty("user")
	private AppleUserData user;

	/**
	 * https://whitepaek.tistory.com/61
	 * <p>
	 * "state":"test",
	 * "code":"c50d317be38c742c0beb19d8743de014c.0.nruy.1NtQvAmp9uhyrsMj1mp7kg",
	 * "id_token":"eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw",
	 * "user":"{
	 * \"email\":\"i79fiit9b3@privaterelay.appleid.com\",
	 * \"name\":{
	 * \"firstName\":\"SEUNGJOO\",
	 * \"lastName\":\"PAEK\"
	 * }
	 */

	public SignInWithAppleDto toServiceDto() {
		return SignInWithAppleDto.of(idToken, authorizationCode, user);
	}
}
