package uk.gov.dwp.uc.pairtest.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidContentsValidator.class)
public @interface ValidContents {
	String message() default "Individual array contents are not valid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
