package org.yixinkang.sagecuisine.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a meal entity in the Sage Cuisine application.
 * It contains information such as the meal's ID, photo, name, price, category,
 * nutrition, and ingredients.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "meal")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "photo", unique = true)
    private String photo;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "category", nullable = false)
    private List<String> category;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Nutrition nutrition;

    @Column(name = "ingredients", nullable = false, length = 500)
    private String ingredients;

    public Meal(String photo, String name, double price, List<String> category, Nutrition nutrition,
            String ingredients) {
        this.photo = photo;
        this.name = name;
        this.price = price;
        this.category = category;
        this.nutrition = nutrition;
        this.ingredients = ingredients;
    }
}
