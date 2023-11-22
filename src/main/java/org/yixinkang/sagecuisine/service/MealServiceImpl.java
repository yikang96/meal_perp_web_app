package org.yixinkang.sagecuisine.service;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yixinkang.sagecuisine.dto.MealDTO;
import org.yixinkang.sagecuisine.dto.NutritionDTO;
import org.yixinkang.sagecuisine.entity.Meal;
import org.yixinkang.sagecuisine.entity.Nutrition;
import org.yixinkang.sagecuisine.entity.User;
import org.yixinkang.sagecuisine.repository.MealRepository;
import org.yixinkang.sagecuisine.repository.NutritionRepository;
import org.yixinkang.sagecuisine.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * This class implements the MealService interface and provides the
 * implementation for all its methods.
 * It interacts with the MealRepository and UserRepository to perform CRUD
 * operations on Meal objects.
 */

@Service
public class MealServiceImpl implements MealService {

    private MealRepository mealRepository;
    private UserRepository userRepository;
    private NutritionRepository nutritionRepository;

    @Autowired
    public MealServiceImpl(MealRepository mealRepository, UserRepository UserRepository,
            NutritionRepository nutritionRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = UserRepository;
        this.nutritionRepository = nutritionRepository;
    }

    /**
     * Adds a new meal to the database.
     *
     * @param mealDTO the DTO object representing the meal to be added
     * @throws Exception if a meal with the same name already exists in the database
     */
    @Transactional
    @Override
    public void addMeal(MealDTO mealDTO) throws Exception {
        if (mealRepository.findMealByName(mealDTO.getName()).isPresent()) {
            throw new Exception("Meal already exists");
        } else {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            Meal meal = modelMapper.map(mealDTO, Meal.class);
            mealRepository.save(meal);
        }

    }

    /**
     * Deletes a meal by its name.
     * If the meal is not found, an exception is thrown.
     *
     * @param name the name of the meal to be deleted
     * @throws Exception if the meal is not found
     */
    @Transactional
    @Override
    public void deleteMealByName(String name) {
        Meal meal = mealRepository.findMealByName(name).orElse(null);
        int mealId = meal.getId();
        int nutritionId = meal.getNutrition().getId();
        mealRepository.delete(meal);
        nutritionRepository.deleteById(nutritionId);

    }

    /**
     * Deletes a meal by its ID and removes it from all users' carts.
     *
     * @param mealId the ID of the meal to be deleted
     * @throws Exception if the meal is not found
     */
    @Transactional
    @Override
    public void deleteMealById(int mealId) throws Exception {
        Meal meal = mealRepository.findMealById(mealId).orElse(null);

        if (meal != null) {
            List<User> users = userRepository.findAll();

            for (User user : users) {
                Map<Meal, Integer> cart = user.getCart();
                if (cart.containsKey(meal)) {
                    cart.remove(meal);
                    user.setCart(cart);
                    userRepository.save(user);
                }
            }
            int nutritionId = meal.getNutrition().getId();
            mealRepository.deleteById(mealId);
            nutritionRepository.deleteById(nutritionId);

        } else {
            throw new Exception("Meal not found");
        }
    }

    /**
     * Updates an existing meal in the database.
     * If the meal with the given ID is found, it is updated with the new
     * information.
     * If the meal with the given ID is not found, an exception is thrown.
     *
     * @param meal the meal object to be updated
     * @throws Exception if the meal with the given ID is not found
     */
    @Transactional
    @Override
    public void updateMeal(Meal meal) throws Exception {

        if (mealRepository.findMealById(meal.getId()).isPresent()) {
            mealRepository.save(meal);
        } else {
            throw new Exception("Meal not found");
        }

    }

    /**
     * Retrieves a list of all meals.
     *
     * @return a list of all meals
     */
    @Transactional
    @Override
    public List<Meal> getAllMeals() {
        List<Meal> meals = mealRepository.findAll();
        return meals;
    }

    /**
     * Retrieves a list of all vegan meals from the meal repository.
     *
     * @return a list of Meal objects that are categorized as vegan.
     */
    @Transactional
    @Override
    public List<Meal> getVeganMeals() {
        List<Meal> meals = mealRepository.findMealByCategoryName("Vegan");
        return meals;
    }

    /**
     * Retrieves a list of meals that belong to the paleo category.
     *
     * @return a list of Meal objects that belong to the paleo category.
     */
    @Transactional
    @Override
    public List<Meal> getPaleoMeals() {
        List<Meal> meals = mealRepository.findMealByCategoryName("Paleo");
        return meals;
    }

    /**
     * Retrieves a list of meals that belong to the keto category.
     *
     * @return a list of meals that belong to the keto category.
     */
    @Transactional
    @Override
    public List<Meal> getKetoMeals() {
        List<Meal> meals = mealRepository.findMealByCategoryName("Keto");
        return meals;
    }

    /**
     * Retrieves a list of Japanese meals from the meal repository.
     *
     * @return a list of Meal objects representing Japanese meals.
     */
    @Transactional
    @Override
    public List<Meal> getJapaneseMeals() {
        List<Meal> meals = mealRepository.findMealByCategoryName("Japanese");
        return meals;
    }

    /**
     * Retrieves a list of meals that belong to the Chinese category.
     *
     * @return a list of Meal objects that belong to the Chinese category.
     */
    @Override
    public List<Meal> getChineseMeals() {
        List<Meal> meals = mealRepository.findMealByCategoryName("Chinese");
        return meals;
    }

    /**
     * Retrieves a list of meals that belong to the Mediterranean category.
     *
     * @return a list of meals that belong to the Mediterranean category
     */
    @Transactional
    @Override
    public List<Meal> getMediterraneanMeals() {
        List<Meal> meals = mealRepository.findMealByCategoryName("Mediterranean");
        return meals;
    }

    /**
     * Represents a meal.
     */
    @Transactional
    @Override
    public Meal findMealByName(String name) {
        Meal meal = mealRepository.findMealByName(name).orElse(null);
        return meal;
    }

    @Transactional
    @Override
    public void updateMealAndNutrition(MealDTO updatedMeal, int mealId) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Nutrition newNutrition = modelMapper.map(updatedMeal.getNutrition(), Nutrition.class);

        Meal originalMeal = mealRepository.findMealById(mealId).get();

        Nutrition originalNutrition = originalMeal.getNutrition();
        originalNutrition.setCalorie(newNutrition.getCalorie());
        originalNutrition.setCarbohydrate(newNutrition.getCarbohydrate());
        originalNutrition.setFat(newNutrition.getFat());
        originalNutrition.setProtein(newNutrition.getProtein());
        originalMeal.setCategory(updatedMeal.getCategory());
        originalMeal.setName(updatedMeal.getName());
        originalMeal.setPrice(updatedMeal.getPrice());
        originalMeal.setIngredients(updatedMeal.getIngredients());
        originalMeal.setName(updatedMeal.getName());
        originalMeal.setPhoto(updatedMeal.getPhoto());
        mealRepository.save(originalMeal);
    }

}
