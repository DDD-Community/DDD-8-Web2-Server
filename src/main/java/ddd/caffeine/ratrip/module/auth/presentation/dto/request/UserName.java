package ddd.caffeine.ratrip.module.auth.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserName {
	private String firstName;
	private String middleName;
	private String lastName;
}
