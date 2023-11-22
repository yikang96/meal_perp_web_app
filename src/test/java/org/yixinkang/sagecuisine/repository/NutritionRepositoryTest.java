package org.yixinkang.sagecuisine.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yixinkang.sagecuisine.entity.Meal;
import org.yixinkang.sagecuisine.entity.Nutrition;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class NutritionRepositoryTest {
    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private NutritionRepository nutritionRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {

        Nutrition nutrition1 = new Nutrition(200, 100, 80, 14);
        Nutrition nutrition2 = new Nutrition(300, 400, 34, 14);
        Nutrition nutrition3 = new Nutrition(200, 400, 56, 14);
        Nutrition nutrition4 = new Nutrition(100, 400, 23, 14);
        Meal meal1 = new Meal("./test photo1", "test meal1", 20.0, Arrays.asList("test", "test meal"), nutrition1,
                "test ingredients");
        Meal meal2 = new Meal("./test photo2", "test meal2", 20.0, Arrays.asList("test", "test meal"), nutrition2,
                "test ingredients");
        Meal meal3 = new Meal("./test photo3", "test meal3", 20.0, Arrays.asList("test", "test meal"), nutrition3,
                "test ingredients");
        Meal meal4 = new Meal("./test photo4", "test meal4", 20.0, Arrays.asList("test", "test meal"), nutrition4,
                "test ingredients");
        entityManager.persist(meal1);
        entityManager.persist(meal2);
        entityManager.persist(meal3);
        entityManager.persist(meal4);
    }

    @AfterEach
    public void cleanUp() {
        Meal meal1 = mealRepository.findMealByName("test meal1").get();
        Meal meal2 = mealRepository.findMealByName("test meal2").get();
        Meal meal3 = mealRepository.findMealByName("test meal3").get();
        Meal meal4 = mealRepository.findMealByName("test meal4").get();
        List<Meal> meals = Arrays.asList(meal1, meal2, meal3, meal4);
        for (Meal meal : meals) {
            if (meal != null) {
                entityManager.remove(meal);
                entityManager.flush();
            }
        }

    }

    @Transactional
    @Test
    public void testFindMealsByCalorie() {
        int calorie = 200;
        List<Object> meals = nutritionRepository.findMealsByCalorie(calorie);
        assertThat(meals.size()).isEqualTo(2);

    }

    @Transactional
    @Test
    public void testFindMealsByCarbohydrate() {
        int carbohydrate = 400;
        List<Object> meals = nutritionRepository.findMealsByCarbohydrate(carbohydrate);

        assertThat(meals.size()).isEqualTo(3);

    }

    @Transactional
    @Test
    public void testFindMealsByProtein() {
        int protein = 34;
        List<Object> meals = nutritionRepository.findMealsByProtein(protein);

        assertThat(meals.size()).isEqualTo(1);
    }

    @Transactional
    @Test
    public void testFindMealsByFat() {
        int fat = 14;
        List<Object> meals = nutritionRepository.findMealsByFat(fat);

        assertThat(meals.size()).isEqualTo(4);
    }

}
