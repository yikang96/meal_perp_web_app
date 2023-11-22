package org.yixinkang.sagecuisine.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a User entity with its properties and methods.
 * It contains the user's id, email, first name, last name, password, roles,
 * enabled status, account lock status,
 * failed attempt count, lock time, and cart.
 * The cart is a map of meals and their corresponding quantities.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "roles", nullable = false, unique = true)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "failed_attempt")
    private int failedAttempt;

    @Column(name = "lock_time")
    private Date lockTime;

    @ElementCollection
    @CollectionTable(name = "user_cart", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "meal_id")
    @Column(name = "count")
    private Map<Meal, Integer> cart;

    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.enabled = true;
        this.cart = new HashMap<Meal, Integer>();
        this.accountNonLocked = true;
        this.failedAttempt = 0;
    }

    public User(String email, String firstName, String lastName, String password, Collection<Role> roles) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
        this.enabled = true;
        this.cart = new HashMap<Meal, Integer>();
        this.accountNonLocked = true;
        this.failedAttempt = 0;
    }
}
