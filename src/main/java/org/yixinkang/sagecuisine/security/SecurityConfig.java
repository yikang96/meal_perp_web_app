package org.yixinkang.sagecuisine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.yixinkang.sagecuisine.service.UserServiceImpl;

//* */

/**
 * This class configures the security settings for the application.
 * It enables web security and defines the authentication provider, password
 * encoder, and security filter chain.
 * It also configures the authorization rules for different endpoints and sets
 * up the login and logout functionality.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        UserServiceImpl userService;

        @Autowired
        private CustomAuthenticationFailureHandler loginFailureHandler;

        @Autowired
        private CustomAuthenticationSuccessHandler loginSuccessHandler;

        /**
         * An implementation of {@link AuthenticationProvider} that retrieves user
         * details from a {@link UserDetailsService}.
         * It also allows for setting a {@link PasswordEncoder} to use when
         * authenticating users.
         */
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
                auth.setUserDetailsService(userService);
                auth.setPasswordEncoder(passwordEncoder());
                return auth;
        }

        /**
         * A password encoder that uses the BCrypt strong hashing function.
         */
        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(12);
        }

        /**
         * A filter chain that handles security-related requests and responses.
         */
        @Bean
        protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(
                                                (auth) -> auth
                                                                .requestMatchers("/home", "/login", "/sign-up",
                                                                                "/about",
                                                                                "/contact", "/css/*", "/js/*",
                                                                                "/images/*", "/create-user")
                                                                .permitAll()
                                                                .requestMatchers("/meals-overview")
                                                                .hasAnyAuthority("USER", "ADMIN")
                                                                .requestMatchers("/add-meal", "/remove-meal",
                                                                                "/update-meal")
                                                                .hasAuthority("ADMIN")
                                                                .anyRequest().authenticated())

                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .usernameParameter("email")
                                                .passwordParameter("password")
                                                .loginProcessingUrl("/login")
                                                .successHandler(loginSuccessHandler)
                                                .failureHandler(loginFailureHandler)
                                                .permitAll())
                                .logout(
                                                logout -> logout
                                                                .invalidateHttpSession(true)
                                                                .clearAuthentication(true)
                                                                .logoutRequestMatcher(
                                                                                new AntPathRequestMatcher("/logout"))
                                                                .permitAll());
                return http.build();
        }

}
