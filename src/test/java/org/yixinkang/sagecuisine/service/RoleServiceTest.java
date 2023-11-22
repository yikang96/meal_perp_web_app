package org.yixinkang.sagecuisine.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yixinkang.sagecuisine.entity.Role;
import org.yixinkang.sagecuisine.repository.RoleRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Assertions;

@SpringBootTest
public class RoleServiceTest {

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    @ParameterizedTest
    @ValueSource(strings = { "MANAGER", "OWNER", "WRITER", "SELLER", "Guest" })
    void testSaveRole(String name) {
        Role role = new Role(name);
        roleService.saveRole(role);
        Role roleActual = roleRepository.findRoleByName(name);
        Assertions.assertEquals(role.getName(), roleActual.getName());
    }

}
