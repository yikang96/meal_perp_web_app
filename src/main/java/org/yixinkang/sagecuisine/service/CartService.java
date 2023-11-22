package org.yixinkang.sagecuisine.service;

import org.springframework.security.core.Authentication;

/**
 * This interface defines the methods for managing a user's cart.
 */
public interface CartService {

    /**
     * Adds a meal to the user's cart.
     *
     * @param mealId         the ID of the meal to add
     * @param authentication the user's authentication information
     * @throws Exception if there is an error adding the meal to the cart
     */
    public void addMealToCart(int mealId, Authentication authentication) throws Exception;

    /**
     * Gets the size of the user's cart.
     * Every user has a cart by default; if the cart is empty, it will be 0;
     * 
     * @param email the email address of the user
     * @return the number of meals in the user's cart
     */
    public int getCartSize(String email);
}
