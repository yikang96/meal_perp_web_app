package org.yixinkang.sagecuisine.service;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yixinkang.sagecuisine.dto.UserDTO;
import org.yixinkang.sagecuisine.entity.Role;
import org.yixinkang.sagecuisine.entity.User;
import org.yixinkang.sagecuisine.repository.RoleRepository;
import org.yixinkang.sagecuisine.repository.UserRepository;
import org.yixinkang.sagecuisine.security.UserPrincipal;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * This class implements the UserService interface and provides the
 * implementation for the methods defined in the interface.
 * It also provides additional methods for managing user accounts such as
 * locking and unlocking accounts, updating failed attempts, and adding/removing
 * roles.
 * The class uses a UserRepository and a RoleRepository to interact with the
 * database and a BCryptPasswordEncoder to encrypt user passwords.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final int MAX_FAILED_ATTEMPTS = 5;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            @Lazy BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;

    }

    /**
     * Interface representing the core user information.
     * Implementations are not used directly by Spring Security for security
     * purposes.
     * They simply store user information which is later encapsulated into
     * Authentication objects.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user == null) {
            log.warn("Invalid username or password {}", email);
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        List<Role> roles = roleRepository.findRoleByUser(user.getId());
        // return new
        // org.springframework.security.core.userdetails.User(user.getEmail(),
        // user.getPassword(),
        // mapRolesToAuthorities(user.getRoles()));
        return new UserPrincipal(user, roles);
    }

    /**
     * Maps a collection of roles to a collection of authorities.
     *
     * @param roles the collection of roles to be mapped
     * @return a collection of authorities mapped from the given roles
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return mapRoles;
    }

    /**
     * Creates a new user with the given UserDTO object.
     * If a user with the same email already exists, throws an exception.
     *
     * @param userDTO the UserDTO object containing the user's information
     * @throws Exception if a user with the same email already exists
     */
    @Override
    @Transactional
    public void createUser(UserDTO userDTO, Optional<List<String>> optionalRoles) throws Exception {

        Optional<User> optionalUser = userRepository.findUserByEmail(userDTO.getEmail());

        if (!optionalUser.isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            User user = modelMapper.map(userDTO, User.class);

            String encryptedPw = encoder.encode(user.getPassword());
            user.setPassword(encryptedPw);

            if (optionalRoles.isPresent()) {
                List<Role> roles = optionalRoles.get().stream()
                        .map(roleName -> roleRepository.findRoleByName(roleName))
                        .collect(Collectors.toList());
                user.setRoles(roles);
            } else {
                user.setRoles(Arrays.asList(roleRepository.findRoleByName("USER")));
            }
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.setFailedAttempt(0);
            userRepository.save(user);
        } else {
            throw new Exception("User with this email already exists.");
        }

    }

    /**
     * Creates a new admin user with the given UserDTO object.
     * If a user with the same email already exists, throws an exception.
     *
     * @param userDTO the UserDTO object containing the user's information
     * @throws Exception if a user with the same email already exists
     */
    @Override
    @Transactional
    public void createUser(UserDTO userDTO) throws Exception {
        this.createUser(userDTO, Optional.empty());
    }

    /**
     * Checks if a user is already in the system.
     */
    @Override
    @Transactional
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    /**
     * Adds a new role to a user.
     *
     * @param roleName the name of the role to be added
     * @param email    the email of the user to whom the role will be added
     */
    @Override
    @Transactional
    public void addNewRoleToUser(String roleName, String email) {
        Role role = roleRepository.findRoleByName(roleName);
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            user.getRoles().add(role);
            userRepository.save(user);
        }

    }

    /**
     * Removes a role from a user.
     *
     * @param roleName the name of the role to be removed
     * @param email    the email of the user from whom the role is to be removed
     */
    @Override
    @Transactional
    public void removeRoleFromUser(String roleName, String email) {
        Role role = roleRepository.findRoleByName(roleName);
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            user.getRoles().remove(role);
            userRepository.save(user);
        }
    }

    /**
     * Unlocks the user account when the lock time has expired based on the user's
     * email.
     * If the user is found and the lock time has expired, the account is unlocked
     * and the lock time,
     * failed attempt count are reset.
     *
     * @param email the email of the user to unlock the account for
     */
    @Override
    @Transactional
    public void unlockUserWhenTimeExpiredByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            Date lockTime = user.getLockTime();
            if (lockTime != null) {
                long lockTimeInMillis = user.getLockTime().getTime();
                long currentTimeInMillis = System.currentTimeMillis();

                if (lockTimeInMillis + LOCK_TIME_DURATION >= currentTimeInMillis) {
                    user.setAccountNonLocked(true);
                    user.setLockTime(null);
                    user.setFailedAttempt(0);
                    userRepository.save(user);
                }
            }

        }

    }

    /**
     * Updates the number of failed login attempts for a user with the given email.
     * If the user is found, their failed attempt count is incremented by 1 and
     * saved to the database.
     *
     * @param email the email of the user to update
     */
    @Override
    public void updateFailedAttemptsByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            user.setFailedAttempt(user.getFailedAttempt() + 1);
            userRepository.save(user);
        }

    }

    @Override
    public void lockUserAccountByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            user.setAccountNonLocked(false);
            user.setLockTime(new Date());
            userRepository.save(user);
        }

    }

    /**
     * Resets the number of failed login attempts for a user with the given email.
     * If no user is found with the given email, nothing happens.
     *
     * @param email the email of the user to reset the failed attempts for
     */
    @Override
    public void resetFailedAttemptsByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            user.setFailedAttempt(0);
            userRepository.save(user);
        }

    }

    /**
     * Disables the user account associated with the given email.
     * Sets the 'enabled' field of the user to false in the database.
     *
     * @param email the email of the user whose account is to be disabled
     */
    @Override
    public void disableAccountByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            user.setEnabled(false);
            userRepository.save(user);
        }
    }

    /**
     * Updates an existing user in the database.
     * If the user is not found, an exception is thrown.
     *
     * @param user the User object to be updated
     * @throws Exception if the user is not found in the database
     */
    @Override
    public void updateUser(User user) throws Exception {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            userRepository.save(user);
        } else {
            logger.warn("User not found");
            throw new Exception("User not found");
        }

    }

}
