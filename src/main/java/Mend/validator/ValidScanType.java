package Mend.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ScanTypeValidator.class)

public @interface ValidScanType {

    String message() default "Invalid scan type provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
