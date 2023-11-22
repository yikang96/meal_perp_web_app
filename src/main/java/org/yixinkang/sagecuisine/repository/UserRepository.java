package org.yixinkang.sagecuisine.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.yixinkang.sagecuisine.entity.User;

import java.util.Optional;

/**
 * This interface represents the repository for User entities.
 * It extends JpaRepository to inherit basic CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their email address.
     * 
     * @param email the email address to search for
     * @return an Optional containing the User object if found, or empty if not
     *         found
     */
    public Optional<User> findUserByEmail(String email);

}
