package org.yixinkang.sagecuisine.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.yixinkang.sagecuisine.dto.MealDTO;
import org.yixinkang.sagecuisine.dto.NutritionDTO;
import org.yixinkang.sagecuisine.entity.Meal;
import org.yixinkang.sagecuisine.service.MealServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a controller for handling HTTP requests related to meals.
 */
@Controller
@Slf4j
public class MealController {

    private static final Logger logger = LoggerFactory.getLogger(MealController.class);

    @Autowired
    private MealServiceImpl mealService;

    /**
     * Represents a sequence of characters. In this context, it is used as the
     * return type of the showMeals method.
     */
    @GetMapping("/meals-overview")
    public String showMeals(@RequestParam(value = "filter", required = false) String filter, Model model) {

        MealDTO mealDTO = new MealDTO();
        mealDTO.setNutrition(new NutritionDTO());
        model.addAttribute("mealDTO", mealDTO);
        // to show users meals
        List<Meal> meals = mealService.getAllMeals();
        if (filter != null && !filter.isEmpty()) {
            switch (filter) {
                case "Vegan":
                    meals = mealService.getVeganMeals();
                    break;
                case "Paleo":
                    meals = mealService.getPaleoMeals();
                    break;
                case "Keto":
                    meals = mealService.getKetoMeals();
                    break;
                case "Mediterranean":
                    meals = mealService.getMediterraneanMeals();
                    break;
                case "Japanese":
                    meals = mealService.getJapaneseMeals();
                    break;
                case "Chinese":
                    meals = mealService.getChineseMeals();
                    break;
                default:
                    meals = mealService.getAllMeals();
            }
        } else {
            meals = mealService.getAllMeals();
        }
        model.addAttribute("meals", meals);
        return "meals-overview";
    }

    /**
     * Adds a meal to the system.
     *
     * @param mealDTO       the meal data transfer object
     * @param bindingResult the binding result
     * @param model         the model
     * @return the view name for the meals overview page
     * @throws Exception if there is an error adding the meal
     */
    @PostMapping("/add-meal")
    public ResponseEntity<?> addMeal(@Valid @ModelAttribute("mealDTO") MealDTO mealDTO, BindingResult bindingResult,
            Model model) throws Exception {

        if (bindingResult.hasErrors()) {
            logger.error("Error binding meal data: {}", bindingResult.getAllErrors());
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", errors));
        }

        try {
            mealService.addMeal(mealDTO);
            logger.info("Successfully add meal with name {}", mealDTO.getName());
            model.addAttribute("mealDTO", new MealDTO());
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Meal added successfully"));
        } catch (Exception e) {
            logger.error("Error adding meal", e);
            Map<String, String> errors = new HashMap<>();
            errors.put("errorMessage", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("errors", errors));
        }

    }

    /**
     * Deletes a meal by its ID.
     *
     * @param body a Map containing the meal ID to be deleted
     * @return a ResponseEntity with HTTP status code 200 if successful, or an error
     *         message with HTTP status code 500 if an exception occurs
     */
    @DeleteMapping("/remove-meal")
    public ResponseEntity<?> removeMeal(@RequestBody Map<String, Integer> body) {
        int mealId = body.get("mealId");
        try {
            mealService.deleteMealById(mealId);
            logger.info("Successfully removed meal with id {}", mealId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error removing meal with id {}", mealId, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing meal in the database.
     *
     * @param updatedMeal the updated meal object to be saved
     * @return ResponseEntity with no content if the update is successful, or a bad
     *         request with an error message if the update fails
     */
    @PutMapping("/update-meal")
    public ResponseEntity<?> updateMeal(@Valid @RequestBody MealDTO updatedMeal, @RequestParam int mealId,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Error binding meal data: {}", bindingResult.getAllErrors());
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", errors));
        }
        try {
            mealService.updateMealAndNutrition(updatedMeal, mealId);
            logger.info("Successfully updated meal with id {}", updatedMeal.getName());
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Meal updated successfully"));
        } catch (Exception e) {
            logger.error("Error updating meal with id {}", updatedMeal.getName(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("errorMessage", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("errors", errorResponse));
        }

    }

}
