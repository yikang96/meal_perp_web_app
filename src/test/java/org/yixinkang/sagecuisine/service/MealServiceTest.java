package org.yixinkang.sagecuisine.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yixinkang.sagecuisine.dto.MealDTO;
import org.yixinkang.sagecuisine.entity.Meal;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MealServiceTest {

    @Autowired
    MealServiceImpl mealService;

    @Transactional
    @Test
    public void testAddMeal() {
        List<String> category = new ArrayList<>();
        category.add("category");
        MealDTO mealDTO = new MealDTO("name", "photo", 1.0, category, null, "ingredients");
        try {
            mealService.addMeal(mealDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Meal actualMeal = mealService.findMealByName("name");
        Assertions.assertEquals("name", actualMeal.getName());
    }

}
