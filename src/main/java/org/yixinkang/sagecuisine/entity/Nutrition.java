package org.yixinkang.sagecuisine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the nutrition information of a food item.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "nutrition")
public class Nutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "calorie")
    private int calorie;

    @Column(name = "carbohydrate")
    private int carbohydrate;

    @Column(name = "protein")
    private int protein;

    @Column(name = "fat")
    private int fat;

    public Nutrition(int calorie, int carbohydrate, int protein, int fat) {
        this.calorie = calorie;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }
}
