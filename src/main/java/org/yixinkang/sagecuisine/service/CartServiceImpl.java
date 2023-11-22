package org.yixinkang.sagecuisine.service;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.yixinkang.sagecuisine.entity.Meal;
import org.yixinkang.sagecuisine.entity.User;
import org.yixinkang.sagecuisine.repository.MealRepository;
import org.yixinkang.sagecuisine.repository.UserRepository;

/**
 * This class implements the CartService interface and provides methods to add
 * meals to a user's cart and get the size of the cart.
 */
@Service
public class CartServiceImpl implements CartService {

    private UserRepository userRepository;

    private MealRepository mealRepository;

    public CartServiceImpl(UserRepository userRepository, MealRepository mealRepository) {
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
    }

    /**
     * Adds a meal to the user's cart.
     *
     * @param mealId         the ID of the meal to be added
     * @param authentication the authentication object of the user
     * @throws Exception if the meal is not found
     */
    @Override
    public void addMealToCart(int mealId, Authentication authentication) throws Exception {
        User user = userRepository.findUserByEmail(authentication.getName()).orElse(null);
        Meal meal = mealRepository.findMealById(mealId).orElse(null);
        if (meal != null) {
            user.getCart().put(meal, user.getCart().getOrDefault(meal, 0) + 1);
            userRepository.save(user);
        } else {
            throw new Exception("Meal not found");
        }

    }

    // every user has a cart by default; if the cart is empty, it will be null
    /**
     * Returns the total number of items in the cart for the given user email.
     * Every user has a cart by default; if the cart is empty, it will be 0.
     *
     * @param email the email of the user whose cart size is to be retrieved
     * @return the total number of items in the cart
     */
    @Override
    public int getCartSize(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);

        Map<Meal, Integer> cart = user.getCart();
        int itemTotal = cart.values().stream().mapToInt(Integer::intValue).sum();
        return itemTotal;
    }
}
