package ddd.caffeine.ratrip.common.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberValidator.class)
public @interface UUID {
	String message() default "올바른 UUID 형식이 아닙니다.";

	Class[] groups() default {};

	Class[] payload() default {};
}