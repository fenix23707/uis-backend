package by.kovzov.uis.academic.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TotalHoursValidator.class)
public @interface TotalHoursCheck {

    String message() default "Total hours must be greater than or equal to the sum of all hours";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
