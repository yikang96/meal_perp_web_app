package org.yixinkang.sagecuisine.service;

import java.util.List;

import org.yixinkang.sagecuisine.dto.MealDTO;
import org.yixinkang.sagecuisine.entity.Meal;

/**
 * This interface represents the service layer for managing meals in the Sage Cuisine application.
 */
/**
 * This interface defines the methods for managing meals in the system.
 */
public interface MealService {

    /**
     * Adds a new meal to the system.
     *
     * @param mealDTO the DTO object representing the meal to be added.
     * @throws Exception
     */
    public void addMeal(MealDTO mealDTO) throws Exception;

    /**
     * Deletes a meal from the system by its name.
     *
     * @param name the name of the meal to be deleted.
     * @throws Exception if the meal is not found in the system.
     */
    public void deleteMealByName(String name);

    /**
     * Deletes a meal from the system by its ID.
     *
     * @param mealId the ID of the meal to be deleted.
     * @throws Exception if the meal is not found in the system.
     */
    public void deleteMealById(int mealId) throws Exception;

    /**
     * Updates an existing meal in the system.
     *
     * @param meal the Meal object representing the updated meal.
     * @throws Exception if the meal is not found in the system.
     */
    public void updateMeal(Meal meal) throws Exception;

    /**
     * Retrieves all meals in the system.
     *
     * @return a list of all meals in the system.
     */
    public List<Meal> getAllMeals();

    /**
     * Retrieves all vegan meals in the system.
     *
     * @return a list of all vegan meals in the system.
     */
    public List<Meal> getVeganMeals();

    /**
     * Retrieves all paleo meals in the system.
     *
     * @return a list of all paleo meals in the system.
     */
    public List<Meal> getPaleoMeals();

    /**
     * Retrieves all keto meals in the system.
     *
     * @return a list of all keto meals in the system.
     */
    public List<Meal> getKetoMeals();

    /**
     * Retrieves all Japanese meals in the system.
     *
     * @return a list of all Japanese meals in the system.
     */
    public List<Meal> getJapaneseMeals();

    /**
     * Retrieves all Chinese meals in the system.
     *
     * @return a list of all Chinese meals in the system.
     */
    public List<Meal> getChineseMeals();

    /**
     * Retrieves all Mediterranean meals in the system.
     *
     * @return a list of all Mediterranean meals in the system.
     */
    public List<Meal> getMediterraneanMeals();

    /**
     * Finds a meal in the system by its name.
     *
     * @param name the name of the meal to be found.
     * @return the Meal object representing the found meal, or null if not found.
     */
    public Meal findMealByName(String name);

    /**
     * Updates the specified meal and its associated nutrition information.
     *
     * @param updatedMeal the updated meal object to be saved
     */
    public void updateMealAndNutrition(MealDTO updatedMeal, int mealId);

}
