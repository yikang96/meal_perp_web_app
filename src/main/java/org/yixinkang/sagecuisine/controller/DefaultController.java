package org.yixinkang.sagecuisine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This class is a controller for the default pages of the Sage Cuisine website.
 */
@Controller
public class DefaultController {

    /**
     * This method returns the home page.
     * @return the name of the home page view
     */
    @GetMapping("/home")
    public String home() {
        return "index";
    }

    /**
     * This method returns the about page.
     * @return the name of the about page view
     */
    @GetMapping("/about")
    public String showAbout() {
        return "about";
    }

    /**
     * This method returns the contact page.
     * @return the name of the contact page view
     */
    @GetMapping("/contact")
    public String showContact() {
        return "contact";
    }

    /**
     * This method returns the meal page.
     * @return the name of the meal page view
     */
    @GetMapping("/meal")
    public String showMeal() {
        return "meal";
    }

}
