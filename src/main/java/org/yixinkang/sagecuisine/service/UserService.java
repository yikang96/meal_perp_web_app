package org.yixinkang.sagecuisine.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.yixinkang.sagecuisine.dto.UserDTO;
import org.yixinkang.sagecuisine.entity.User;

/**
 * This interface defines the methods for managing user accounts and roles.
 */
public interface UserService extends UserDetailsService {

    /**
     * Creates a new user account with the given user information and roles.
     *
     * @param user          the user information to create the account with
     * @param optionalRoles the roles to create the account with
     * @throws Exception if there is an error creating the account
     */
    public void createUser(UserDTO user, Optional<List<String>> optionalRoles) throws Exception;

    /**
     * Creates a new user account with the given user information.
     *
     * @param userDTO the user information to create the account with
     * @throws Exception if there is an error creating the account
     */
    public void createUser(UserDTO userDTO) throws Exception;

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user to find
     * @return the user with the given email address, or null if no user is found
     */
    public User findUserByEmail(String email);

    /**
     * Updates an existing user account with the given user information.
     *
     * @param user the user information to update the account with
     * @throws Exception if there is an error updating the account
     */
    public void updateUser(User user) throws Exception;

    /**
     * Adds a new role to a user account.
     *
     * @param roleName the name of the role to add
     * @param email    the email address of the user to add the role to
     */
    public void addNewRoleToUser(String roleName, String email);

    /**
     * Removes a role from a user account.
     *
     * @param roleName the name of the role to remove
     * @param email    the email address of the user to remove the role from
     */
    public void removeRoleFromUser(String roleName, String email);

    /**
     * Updates the number of failed login attempts for a user account.
     *
     * @param email the email address of the user to update the failed attempts for
     */
    public void updateFailedAttemptsByEmail(String email);

    /**
     * Locks a user account after too many failed login attempts.
     *
     * @param email the email address of the user to lock the account for
     */
    public void lockUserAccountByEmail(String email);

    /**
     * Unlocks a user account after a certain amount of time has passed since it was
     * locked.
     *
     * @param email the email address of the user to unlock the account for
     */
    public void unlockUserWhenTimeExpiredByEmail(String email);

    /**
     * Resets the number of failed login attempts for a user account.
     *
     * @param email the email address of the user to reset the failed attempts for
     */
    public void resetFailedAttemptsByEmail(String email);

    /**
     * Disables a user account.
     *
     * @param email the email address of the user to disable the account for
     */
    public void disableAccountByEmail(String email);

}
