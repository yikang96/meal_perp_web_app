package org.yixinkang.sagecuisine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.yixinkang.sagecuisine.service.CartService;

/**
 * This class provides advice for the cart, including populating the item total in the cart.
 */
@ControllerAdvice
public class CartAdvice {

    @Autowired
    private CartService cartService;

    /**
     * Populates the item total in the cart for the authenticated user.
     * @param authentication the authentication object for the current user
     * @return the total number of items in the cart for the authenticated user
     */
    @ModelAttribute("itemTotal")
    public int populateItemTotal(Authentication authentication) {
        if (authentication != null) {
            return cartService.getCartSize(authentication.getName());
        } else {
            return 0;
        }
    }

}
