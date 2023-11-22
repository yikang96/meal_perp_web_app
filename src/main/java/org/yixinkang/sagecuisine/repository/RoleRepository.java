package org.yixinkang.sagecuisine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yixinkang.sagecuisine.entity.Role;

/**
 * This interface represents the repository for the Role entity.
 * It extends the JpaRepository interface and provides methods for CRUD
 * operations on Role entities.
 * It also includes custom queries for finding a Role by name and finding all
 * Roles associated with a specific user.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Finds a role by its name.
     *
     * @param role the name of the role to find
     * @return the Role object representing the found role, or null if no role was
     *         found
     */
    public Role findRoleByName(String role);

    /**
     * Finds the role of a user by their ID.
     *
     * @param id the ID of the user
     * @return a list of roles associated with the user
     */
    @Query(value = "select * from role where role.id= (select role_id from user_roles where user_id = :id)", nativeQuery = true)
    public List<Role> findRoleByUser(@Param("id") int id);
}
