package org.yixinkang.sagecuisine.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents the nutrition information of a food item.
 * It contains the calorie, carbohydrate, protein, and fat values of the food
 * item.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class NutritionDTO {

    @Min(value = 1, message = "Calorie value must be provided")
    private int calorie;

    @Min(value = 1, message = "Carbohydrate value must be provided")
    private int carbohydrate;

    @Min(value = 1, message = "Protein value must be provided")
    private int protein;

    @Min(value = 1, message = "Fat value must be provided")
    private int fat;

}
