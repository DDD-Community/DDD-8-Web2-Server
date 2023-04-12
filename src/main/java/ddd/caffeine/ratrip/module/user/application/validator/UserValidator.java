package ddd.caffeine.ratrip.module.user.application.validator;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.Optional;

import org.springframework.stereotype.Component;

import ddd.caffeine.ratrip.common.exception.domain.UserException;
import ddd.caffeine.ratrip.module.user.domain.User;

@Component
public class UserValidator {
	public User validateExistUser(Optional<User> user) {
		return user.orElseThrow(() -> new UserException(NOT_FOUND_USER_EXCEPTION));
	}
}
