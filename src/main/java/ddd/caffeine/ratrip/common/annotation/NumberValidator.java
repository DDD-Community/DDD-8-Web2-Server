package ddd.caffeine.ratrip.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<Number, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			Long.parseLong(value);
		} catch (NumberFormatException e) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}
