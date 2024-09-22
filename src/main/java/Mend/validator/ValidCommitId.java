package Mend.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CommitIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCommitId {

    String message() default "Invalid commit ID provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
