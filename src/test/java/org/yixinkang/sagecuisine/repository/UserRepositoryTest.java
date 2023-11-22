package org.yixinkang.sagecuisine.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yixinkang.sagecuisine.entity.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindUserByEmail() {
        User user = userRepository.findUserByEmail("123@test.com").orElse(null);
        Assertions.assertEquals(null, user);
    }
}
