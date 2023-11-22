package org.yixinkang.sagecuisine.validation;

import org.yixinkang.sagecuisine.dto.NutritionDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NutritionValidator implements ConstraintValidator<ValidNutrition, NutritionDTO> {

    @Override
    public void initialize(ValidNutrition constraintAnnotation) {
    }

    @Override
    public boolean isValid(NutritionDTO nutrition, ConstraintValidatorContext context) {
        return nutrition != null && nutrition.getProtein() != 0 && nutrition.getFat() != 0
                && nutrition.getCarbohydrate() != 0 && nutrition.getCalorie() != 0;
    }
}
