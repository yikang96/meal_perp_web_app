package org.yixinkang.sagecuisine.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.yixinkang.sagecuisine.entity.Meal;
import org.yixinkang.sagecuisine.entity.Role;
import org.yixinkang.sagecuisine.validation.FieldMatch;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Transfer Object for User entity.
 * Contains user information such as email, first name, last name, password,
 * matching password, role, and cart.
 * Provides validation for password and matching password fields.
 */
@Component
@Setter
@Getter
@NoArgsConstructor
@ToString
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match") })
public class UserDTO {
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty
    @Pattern(regexp = "[A-Za-z]+", message = "Only alphabetic characters allowed")
    private String firstName;
    @NotEmpty
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String lastName;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @NotEmpty
    private String matchPassword;

    private Collection<Role> roles;

    private Map<Meal, Integer> cart;

    public UserDTO(@NotEmpty(message = "Email cannot be empty") String email,
            @NotEmpty(message = "Only alphabetic allowed") String firstName,
            @NotEmpty(message = "Only alphabetic allowed") String lastName,
            @NotEmpty(message = "Password should not be empty") String password, @NotEmpty String matchPassword) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.matchPassword = matchPassword;
        this.cart = new HashMap<Meal, Integer>();
    }
}
