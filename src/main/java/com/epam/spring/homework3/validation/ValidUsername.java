package com.epam.spring.homework3.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {

    String message() default "'Username' must be unique!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
