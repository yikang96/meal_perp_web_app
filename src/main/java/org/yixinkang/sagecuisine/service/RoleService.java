package org.yixinkang.sagecuisine.service;

import java.util.List;

import org.yixinkang.sagecuisine.entity.Role;

/**
 * This interface defines the methods to be implemented by a RoleService class.
 */
public interface RoleService {

    /**
     * Saves a Role object to the database.
     * @param role The Role object to be saved.
     */
    public void saveRole(Role role);

    /**
     * Finds a Role object in the database by its name.
     * @param name The name of the Role object to be found.
     * @return The Role object with the given name, or null if not found.
     * @throws Exception
     */
    public Role findRoleByRoleName(String name) throws Exception;

    /**
     * Gets a list of Role objects associated with a given user.
     * @param id The ID of the user to get the roles for.
     * @return A list of Role objects associated with the given user.
     */
    public List<Role> getRolesByUser(int id);

}
