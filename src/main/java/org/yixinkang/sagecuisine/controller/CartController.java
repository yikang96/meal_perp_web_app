package org.yixinkang.sagecuisine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yixinkang.sagecuisine.entity.Meal;
import org.yixinkang.sagecuisine.entity.User;
import org.yixinkang.sagecuisine.repository.MealRepository;
import org.yixinkang.sagecuisine.service.CartService;
import org.yixinkang.sagecuisine.service.UserService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * This class represents the controller for the shopping cart functionality of
 * the application.
 * It handles adding, updating, and removing meals from the user's cart, as well
 * as calculating the subtotal of the cart.
 */
@Controller
@Slf4j
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    private MealRepository mealRepository;

    private CartService cartService;

    @Autowired
    public CartController(UserService userService, MealRepository mealRepository, CartService cartService) {
        this.userService = userService;
        this.mealRepository = mealRepository;
        this.cartService = cartService;
    }

    /**
     * Calculates the subtotal of the user's cart based on the prices of the meals
     * and their quantities.
     *
     * @param authentication the authentication object containing the user's email
     * @return the subtotal of the user's cart
     */
    @ModelAttribute("subtotal")
    public double getSubtotal(Authentication authentication) {
        User user = userService.findUserByEmail(authentication.getName());
        double subtotal = 0;
        Set<Meal> meals = user.getCart().keySet();
        for (Meal meal : meals) {
            subtotal += meal.getPrice() * user.getCart().get(meal);
        }
        return subtotal;
    }

    /**
     * Returns the name of the view to be rendered, which is "shopping-cart".
     *
     * @return the name of the view to be rendered
     */
    @GetMapping("/my-shopping-cart")
    public String showMyshoppingCart(Model model, Authentication authentication) {
        User user = userService.findUserByEmail(authentication.getName());
        model.addAttribute("myCart", user.getCart());
        logger.info("User cart is here");
        return "shopping-cart";
    }

    /**
     * Adds a meal to the cart and redirects to the meals overview page.
     *
     * @param mealId             the ID of the meal to add to the cart
     * @param authentication     the authentication object for the current user
     * @param redirectAttributes the redirect attributes for the request
     * @return a string representing the redirect URL for the meals overview page
     */
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam int mealId, Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            cartService.addMealToCart(mealId, authentication);
            logger.info("Successfully added meal with id {} to cart for user {}", mealId, authentication.getName());
            return "redirect:/meals-overview";
        } catch (Exception e) {
            logger.error("Error adding meal with id {} to cart for user {}", mealId, authentication.getName(), e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/meals-overview";
        }

    }

    /**
     * Updates the quantity of a meal in the user's cart.
     *
     * @param mealId         the ID of the meal to update
     * @param quantity       the new quantity of the meal
     * @param authentication the authentication object of the user
     * @return a ResponseEntity with a message indicating success or failure
     */
    @PatchMapping("/update-meal-quantity")
    public ResponseEntity<?> updateMealQuantity(@RequestParam int mealId, @RequestParam int quantity,
            Authentication authentication) {
        logger.info("Transaction started for updating meal quantity with id {} from cart for user {}", mealId,
                authentication.getName());
        // Get the meal from the database
        Meal meal = mealRepository.findMealById(mealId).orElse(null);

        try {
            User user = userService.findUserByEmail(authentication.getName());
            if (user.getCart().containsKey(meal)) {
                user.getCart().put(meal, quantity);
                userService.updateUser(user);
                logger.info("Successfully updated quantity for meal with id {} to {} for user {}", mealId, quantity,
                        authentication.getName());
                return ResponseEntity.ok("Meal quantity updated");
            } else {
                logger.warn("Attempted to update quantity for meal with id {} that is not in the cart for user {}",
                        mealId, authentication.getName());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meal not found in cart");
            }
        } catch (Exception e) {
            logger.error("Error updating quantity for meal with id {} to {} for user {}", mealId, quantity,
                    authentication.getName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating meal quantity");
        }
    }

    /**
     * Removes a meal from the user's cart.
     *
     * @param mealId         the ID of the meal to be removed
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with a message indicating success or failure
     */
    @Transactional
    @DeleteMapping("/remove-meal-from-cart")
    public ResponseEntity<?> removeMeal(@RequestParam int mealId, Authentication authentication) {

        logger.info("Transaction started for removing meal with id {} from cart for user {}", mealId,
                authentication.getName());
        // Get the meal from the database
        Meal meal = mealRepository.findMealById(mealId).orElse(null);

        try {
            User user = userService.findUserByEmail(authentication.getName());
            if (user.getCart().containsKey(meal)) {
                user.getCart().remove(meal);
                userService.updateUser(user);
                logger.info("Successfully removed meal with id {} from cart for user {}", mealId,
                        authentication.getName());
                return ResponseEntity.ok("Meal removed from cart");
            } else {
                logger.warn("Attempted to remove meal with id {} that is not in the cart for user {}", mealId,
                        authentication.getName());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meal not found in cart");
            }
        } catch (Exception e) {
            logger.error("Error removing meal with id {} from cart for user {}", mealId, authentication.getName(),
                    e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing meal from cart");
        }
    }

}
