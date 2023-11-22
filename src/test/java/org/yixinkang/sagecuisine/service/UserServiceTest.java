package org.yixinkang.sagecuisine.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yixinkang.sagecuisine.dto.UserDTO;
import org.yixinkang.sagecuisine.entity.User;

import jakarta.transaction.Transactional;

/**
 * This class contains JUnit tests for the UserService class.
 * It tests the createUser, addNewRoleToUser, and removeRoleFromUser methods.
 */
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserServiceImpl userService;

    @Transactional
    @ParameterizedTest
    @CsvSource({ "casey@test.com, casey, dutton, 123Abc@@, 123Abc@@",
            "kitty@12Test.com, kitty, Jackson, kitty@12Test.com, kitty@12Test.com",
            "Manager@89test.com, Manager, Spencer,Manager@89test.com, Manager@89test.com"
    })
    public void testCreateUser(String email, String firstName, String lastName, String password, String matchPassword)
            throws Exception {
        UserDTO userDTO = new UserDTO(email, firstName, lastName, password, matchPassword);
        userService.createUser(userDTO);
        User actualUser = userService.findUserByEmail(userDTO.getEmail());
        Assertions.assertNotNull(actualUser.getEmail(), userDTO.getEmail());
    }

}
