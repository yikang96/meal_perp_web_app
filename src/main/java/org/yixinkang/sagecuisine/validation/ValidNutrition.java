package org.yixinkang.sagecuisine.validation;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NutritionValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNutrition {
    String message() default "Invalid nutrition";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
