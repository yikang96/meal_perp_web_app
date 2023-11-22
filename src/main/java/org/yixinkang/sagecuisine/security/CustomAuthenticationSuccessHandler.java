package org.yixinkang.sagecuisine.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.yixinkang.sagecuisine.entity.User;
import org.yixinkang.sagecuisine.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class is responsible for handling successful authentication requests.
 * It extends SimpleUrlAuthenticationSuccessHandler and overrides the
 * onAuthenticationSuccess method
 * to reset the failed attempts count for the user if it is greater than 0.
 * It also sets the default target URL after successful authentication.
 */
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserServiceImpl userService;

    public CustomAuthenticationSuccessHandler(UserServiceImpl userService) {
        this.userService = userService;
        setDefaultTargetUrl("/home"); // Set the default target URL after successful authentication
    }

    /**
     * This method is called when a user successfully logs in. It resets the failed
     * login attempts for the user.
     *
     * @param request        the HTTP request
     * @param response       the HTTP response
     * @param authentication the authentication object containing the user's
     *                       information
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        if (user.getFailedAttempt() > 0) {
            userService.resetFailedAttemptsByEmail(email);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
