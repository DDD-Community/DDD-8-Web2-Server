package ddd.caffeine.ratrip.common.annotation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullableUUIDValidator implements ConstraintValidator<NullableUUIDFormat, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		final Pattern UUID_PATTERN = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
		if (value == null) {
			return Boolean.TRUE;
		}
		return UUID_PATTERN.matcher(value).matches();
	}
}
