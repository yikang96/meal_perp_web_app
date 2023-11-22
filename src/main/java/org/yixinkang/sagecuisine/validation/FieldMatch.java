/**
 * This annotation is used to validate that two fields in a class have the same value.
 * It is used in conjunction with FieldMatchValidator.
 */
package org.yixinkang.sagecuisine.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = FieldMatchValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMatch {
   String message() default "";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};

   String first();

   String second();

   @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   @interface List {
      FieldMatch[] value();
   }
}
