package ddd.caffeine.ratrip.common.annotation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UUIDValidator implements ConstraintValidator<UUIDFormat, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		final Pattern UUID_PATTERN = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");

		return UUID_PATTERN.matcher(value).matches();
	}
}
