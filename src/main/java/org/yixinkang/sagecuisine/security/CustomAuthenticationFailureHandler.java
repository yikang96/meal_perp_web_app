package org.yixinkang.sagecuisine.security;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.yixinkang.sagecuisine.entity.User;
import org.yixinkang.sagecuisine.repository.UserRepository;
import org.yixinkang.sagecuisine.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class is a custom authentication failure handler that extends
 * SimpleUrlAuthenticationFailureHandler.
 * It handles the authentication failure by updating the failed attempts of the
 * user and locking the user account
 * if the maximum number of failed attempts is reached. It also checks if the
 * user account is locked and unlocks it
 * if the lock time has expired. If the user doesn't exist, it throws a
 * BadCredentialsException.
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    public CustomAuthenticationFailureHandler(UserServiceImpl userService) {
        this.userService = userService;
        setDefaultFailureUrl("/login?error=true"); // Set the default failure URL
    }

    /**
     * This method is called when a user fails to authenticate. It updates the
     * failed attempts count for the user and locks the account if the maximum
     * number of failed attempts is reached. It also throws exceptions for invalid
     * credentials or locked accounts.
     *
     * @param request   the HTTP request
     * @param response  the HTTP response
     * @param exception the authentication exception that occurred
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("email");

        User user = userRepository.findUserByEmail(email).orElse(null);

        if (user != null && user.isEnabled()) {

            int failedAttempts = user.getFailedAttempt();
            userService.updateFailedAttemptsByEmail(email);

            if (failedAttempts < UserServiceImpl.MAX_FAILED_ATTEMPTS - 1) {

                exception = new BadCredentialsException("Invalid username or password. You have " +
                        (UserServiceImpl.MAX_FAILED_ATTEMPTS - failedAttempts - 1) +
                        " attempt(s) left.");

            } else if (failedAttempts == UserServiceImpl.MAX_FAILED_ATTEMPTS) {
                userService.lockUserAccountByEmail(email);
                Date lockTime = user.getLockTime();

                exception = new LockedException(
                        "Your account has been locked due to 5 failed attempts. It will be unlocked after 24 hours from "
                                + lockTime);
            }
            if (!user.isAccountNonLocked()) {
                userService.unlockUserWhenTimeExpiredByEmail(email);
            }
        } else {
            exception = new BadCredentialsException("User doesn't exist, please verify if the email is correct.");
        }

        super.onAuthenticationFailure(request, response, exception);

    }
}
