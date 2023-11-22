package org.yixinkang.sagecuisine.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yixinkang.sagecuisine.dto.UserDTO;
import org.yixinkang.sagecuisine.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * This class serves as the controller for user-related operations, such as
 * login, sign-up, and user creation.
 */
@Controller
@Slf4j
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login() {
        logger.info("Processing login request");
        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        logger.info(" New user account with email {} has been created", userDTO.getEmail());
        return "sign-up";
    }

    /**
     * Processes the sign-up form submission and creates a new user if there are no
     * validation errors.
     *
     * @param userDTO       the user data transfer object containing the user's
     *                      information
     * @param bindingResult the result of the validation performed on the userDTO
     *                      object
     * @param model         the model object used to pass data to the view
     * @return the name of the view to display after the form submission
     * @throws Exception if there is an error creating the user
     */
    @PostMapping("/create-user")
    public String signUpProcess(@ModelAttribute("userDTO") @Valid UserDTO userDTO, BindingResult bindingResult,
            Model model)
            throws Exception {

        if (bindingResult.hasErrors()) {
            logger.warn("User wrong input " +
                    Arrays.toString(bindingResult.getSuppressedFields()));
            return "sign-up";
        }

        try {
            logger.info("Received user data: " + userDTO);
            userService.createUser(userDTO);
            return "redirect:/home";
        } catch (Exception e) {
            bindingResult.rejectValue("email", "error.userDTO", e.getMessage());
            model.addAttribute("errorMessage", "Email already exists. Please choose a different email.");
            return "sign-up";
        }

    }

}
