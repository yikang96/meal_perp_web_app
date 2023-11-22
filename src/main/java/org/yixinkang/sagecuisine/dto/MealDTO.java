package org.yixinkang.sagecuisine.dto;

import java.util.List;

import lombok.*;
import org.springframework.stereotype.Component;
import org.yixinkang.sagecuisine.validation.ValidNutrition;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * This class represents a Meal Data Transfer Object (DTO) which contains
 * information about a meal.
 */
@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MealDTO {

    @NotBlank(message = "Please enter the name of the meal")
    private String name;

    @NotBlank(message = "Please put the correct path of the photo")
    private String photo;

    @Min(value = 1, message = "Please enter a price")
    private double price;

    @Size(min = 1, message = "Please select at least one category")
    private List<String> category;

    @ValidNutrition(message = "Please provide all nutrition values")
    private NutritionDTO nutrition;

    @NotBlank(message = "please write all the ingredients")
    private String ingredients;
}
