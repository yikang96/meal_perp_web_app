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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MealRepositoryTest {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private EntityManager entityManager;

    private Meal meal;

    @BeforeEach
    public void setUp() {
        Nutrition nutrition = new Nutrition(10, 10, 10, 10);
        Meal meal = new Meal("./test photo", "test meal", 10.0, Arrays.asList("test", "test meal"), nutrition,
                "test ingredients");
        entityManager.persist(meal);
    }

    @AfterEach
    public void cleanUp() {
        Meal meal = mealRepository.findMealByName("test meal").get();
        if (meal != null) {
            entityManager.remove(meal);
            entityManager.flush();
        }
    }

    @Transactional
    @Test
    public void testFindMealByName() {

        Optional<Meal> found = mealRepository.findMealByName("test meal");

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo("test meal");
    }

    @Transactional
    @Test
    public void testFindMealById() {

        int id = mealRepository.findMealByName("test meal").get().getId();
        Optional<Meal> found = mealRepository.findMealById(id);

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getId()).isEqualTo(id);
    }

    @Transactional
    @Test
    public void testFindMealByCategoryName() {

        List<Meal> found = mealRepository.findMealByCategoryName("test");
        List<Meal> found1 = mealRepository.findMealByCategoryName("test meal");
        assertThat(found.size()).isEqualTo(1);
        assertThat(found1.size()).isEqualTo(1);

        assertThat(found.get(0).getName()).isEqualTo("test meal");
        assertThat(found1.get(0).getCategory()).isEqualTo(Arrays.asList("test", "test meal"));
    }

}
