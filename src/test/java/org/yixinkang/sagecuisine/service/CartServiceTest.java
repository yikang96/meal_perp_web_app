package org.yixinkang.sagecuisine.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yixinkang.sagecuisine.dto.MealDTO;
import org.yixinkang.sagecuisine.dto.UserDTO;
import org.yixinkang.sagecuisine.entity.Meal;
import org.yixinkang.sagecuisine.entity.User;
import org.yixinkang.sagecuisine.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    MealServiceImpl mealService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Test
    public void testAddMealToCart() throws Exception {
        UserDTO userDTO = new UserDTO("Manager@89test.com", "Manager", "Spencer", "Manager@89test.com",
                "Manager@89test.com");

        userService.createUser(userDTO);
        List<String> category = new ArrayList<>();
        category.add("category");
        MealDTO mealDTO = new MealDTO("/name", "photo", 1.0, category, null, "ingredients");
        mealService.addMeal(mealDTO);
        Meal meal = mealService.findMealByName(mealDTO.getName());
        User updatedUser = userRepository.findUserByEmail(userDTO.getEmail()).orElse(null);
        updatedUser.getCart().put(meal, updatedUser.getCart().getOrDefault(meal, 0) + 1);
        assertThat(updatedUser.getCart()).containsKey(meal);
        assertThat(updatedUser.getCart().get(meal)).isEqualTo(1);
    }
}
