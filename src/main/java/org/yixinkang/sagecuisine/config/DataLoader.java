package org.yixinkang.sagecuisine.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yixinkang.sagecuisine.dto.UserDTO;
import org.yixinkang.sagecuisine.dto.MealDTO;
import org.yixinkang.sagecuisine.dto.NutritionDTO;
import org.yixinkang.sagecuisine.service.UserService;
import org.yixinkang.sagecuisine.service.MealService;
import org.yixinkang.sagecuisine.repository.RoleRepository;
import org.yixinkang.sagecuisine.entity.Role;

@Configuration
public class DataLoader {

    private RoleRepository roleRepository;
    private UserService userService;
    private MealService mealService;

    @Autowired
    public DataLoader(RoleRepository roleRepository, UserService userService, MealService mealService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.mealService = mealService;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // Create roles if they don't exist
            List<String> roleNames = new ArrayList<>();
            roleNames.add("USER");
            roleNames.add("ADMIN");
            for (String roleName : roleNames) {
                if (roleRepository.findRoleByName(roleName) == null) {
                    roleRepository.save(new Role(roleName));
                }
            }

            // Create users if they don't exist
            UserDTO userDTO = new UserDTO("Kim12@test.com", "Kim", "Smith", "Kim12@test.com", "Kim12@test.com");
            UserDTO adminDTO = new UserDTO("Admin12@test.com", "Admin", "Smith", "Admin12@test.com",
                    "Admin12@test.com");

            if (userService.findUserByEmail(userDTO.getEmail()) == null) {
                userService.createUser(userDTO);
            }
            if (userService.findUserByEmail(adminDTO.getEmail()) == null) {
                List<String> adminRoles = new ArrayList<>();
                adminRoles.add("ADMIN");
                userService.createUser(adminDTO, Optional.of(adminRoles));
            }

            // Create meals if they don't exist

            NutritionDTO nutrition1 = new NutritionDTO(275, 30, 9, 5);
            List<String> category1 = new ArrayList<>();
            category1.add("Japanese");
            MealDTO meal1 = new MealDTO("Chico Ramen",
                    "./images/meal-ramen.jpg", 15, category1, nutrition1,
                    "Ramen noodles, Garlic and ginger, Broth (chicken or veg), Dried shiitake mushrooms, Veggies like carrots or kale");

            NutritionDTO nutrition2 = new NutritionDTO(225, 25, 10, 5);
            List<String> category2 = new ArrayList<>();
            category2.add("Vegan");
            MealDTO meal2 = new MealDTO("Lentil Soup",
                    "./images/meal-vegan-lentil-soup.jpg", 15, category2, nutrition2,
                    "dried green lentils,  kombu, canola oil, yellow onion, carrots, balsamic vinegar,  sea salt");

            NutritionDTO nutrition3 = new NutritionDTO(290, 45, 12, 9);
            List<String> category3 = new ArrayList<>();
            category3.add("Mediterranean");
            MealDTO meal3 = new MealDTO("Tunisian Chickpea Stew",
                    "./images/meal-mediterranean.jpg", 17, category3, nutrition3,
                    "Chickpeas, Rustic bread, Extra virgin olive oil, Aromatics,  Salt, cumin, coriander, and paprika, Harissa paste, Lemons");

            NutritionDTO nutrition4 = new NutritionDTO(265, 36, 9, 5);
            List<String> category4 = new ArrayList<>();
            category4.add("Chinese");
            MealDTO meal4 = new MealDTO("Mapo Tofu",
                    "./images/meal-chinese.jpg", 13, category4, nutrition4,
                    "Thai bird chili peppers, dried red chilies,  Sichuan peppercorns, ginger, garlic, pork, spicy bean sauce,  chicken broth, silken tofu, cornstarch, scallion");

            NutritionDTO nutrition5 = new NutritionDTO(230, 20, 11, 5);
            List<String> category5 = new ArrayList<>();
            category5.add("Keto");
            MealDTO meal5 = new MealDTO("Baked salmon",
                    "./images/meal-keto.jpg", 15, category5, nutrition5,
                    "Roasted Brussels sprouts, Baked salmon, Horseradish sauce");

            NutritionDTO nutrition6 = new NutritionDTO(300, 60, 9, 5);
            List<String> category6 = new ArrayList<>();
            category6.add("Paleo");
            category6.add("Mediterranean");
            MealDTO meal6 = new MealDTO("Shrimp veggie cream soup",
                    "./images/meal-paleo.jpg", 15, category6, nutrition6,
                    "Shrimp, green veggies, cream, salt, pepper");

            List<MealDTO> meals = new ArrayList<>();
            meals.add(meal1);
            meals.add(meal2);
            meals.add(meal3);
            meals.add(meal4);
            meals.add(meal5);
            meals.add(meal6);

            for (MealDTO meal : meals) {
                if (mealService.findMealByName(meal.getName()) == null) {
                    mealService.addMeal(meal);
                }

            }

        };
    }

}
