package org.yixinkang.sagecuisine.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.yixinkang.sagecuisine.entity.Role;
import org.yixinkang.sagecuisine.entity.User;

/**
 * UserPrincipal class implements UserDetails interface and represents the
 * authenticated user.
 * It contains the user object and a list of roles assigned to the user.
 */
public class UserPrincipal implements UserDetails {

    private User user;
    private List<Role> roles;

    public UserPrincipal(User user, List<Role> roles) {
        super();
        this.user = user;
        this.roles = roles;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of authorities granted to the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user.
     */
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * Returns the email address of the user associated with this UserPrincipal.
     *
     * @return the email address of the user
     */
    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot
     * be authenticated.
     *
     * @return true if the user's account is valid (ie non-expired), false if no
     *         longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Returns a boolean indicating whether the user account is locked or not.
     *
     * @return true if the user account is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     * Always returns true as this functionality is not implemented.
     *
     * @return always returns true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Returns a boolean indicating whether the user is enabled or not.
     *
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}
