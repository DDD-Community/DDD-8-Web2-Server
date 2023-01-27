package ddd.caffeine.ratrip.module.auth.presentation.dto.request;

import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AppleProfile {
	@Email
	private String email;
	private UserName name;

}
