package org.yixinkang.sagecuisine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yixinkang.sagecuisine.entity.Role;
import org.yixinkang.sagecuisine.repository.RoleRepository;

/**
 * Implementation of the RoleService interface that provides methods for
 * managing roles.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Saves a role to the database.
     *
     * @param role the role to be saved
     */
    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to find
     * @return the role with the given name, or null if no such role exists
     * @throws Exception
     */
    @Override
    @Transactional
    public Role findRoleByRoleName(String name) throws Exception {
        if (roleRepository.findRoleByName(name) == null) {
            throw new Exception("Role not found");
        } else {
            return roleRepository.findRoleByName(name);
        }

    }

    /**
     * Gets a list of roles associated with a user.
     *
     * @param id the ID of the user to get roles for
     * @return a list of roles associated with the given user
     */
    @Override
    @Transactional
    public List<Role> getRolesByUser(int id) {
        return roleRepository.findRoleByUser(id);
    }

}
