package org.yixinkang.sagecuisine.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yixinkang.sagecuisine.entity.Role;

@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    // Testing against the real database
    public void testFindRoleByName() {

        Role expectedRole = new Role("USER");
        Role actualRole = roleRepository.findRoleByName("USER");
        Assertions.assertEquals(expectedRole.getName(), actualRole.getName());
    }

}
