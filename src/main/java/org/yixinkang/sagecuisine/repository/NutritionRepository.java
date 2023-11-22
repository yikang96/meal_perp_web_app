package org.yixinkang.sagecuisine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yixinkang.sagecuisine.entity.Nutrition;

/**
 * This interface represents the repository for Nutrition entities.
 * It extends JpaRepository which provides all the CRUD operations for
 * Nutrition.
 */
@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Integer> {

    /**
     * Finds all meals that have a nutrition with the given calorie count.
     *
     * @param calorie the calorie count to search for
     * @return a list of meals with a nutrition matching the given calorie count
     */
    @Query(value = "SELECT * FROM meal WHERE nutrition_id IN (SELECT id FROM nutrition WHERE calorie = :calorie)", nativeQuery = true)
    public List<Object> findMealsByCalorie(@Param("calorie") int calorie);

    /**
     * Finds a list of meals that have a nutrition with the specified carbohydrate
     * value.
     *
     * @param carbohydrate the carbohydrate value to search for
     * @return a list of meals with the specified carbohydrate value
     */
    @Query(value = "SELECT * FROM meal WHERE nutrition_id IN (SELECT id FROM nutrition WHERE carbohydrate = :carbohydrate)", nativeQuery = true)
    public List<Object> findMealsByCarbohydrate(@Param("carbohydrate") int carbohydrate);

    /**
     * Finds a list of meals that have the specified amount of protein.
     *
     * @param protein the amount of protein to search for
     * @return a list of meals that have the specified amount of protein
     */
    @Query(value = "SELECT * FROM meal WHERE nutrition_id IN (SELECT id FROM nutrition WHERE protein = :protein)", nativeQuery = true)
    public List<Object> findMealsByProtein(@Param("protein") int protein);

    /**
     * Finds a list of meals that have a nutrition with the specified fat value.
     *
     * @param fat the fat value to search for
     * @return a list of meals with the specified fat value
     */
    @Query(value = "SELECT * FROM meal WHERE nutrition_id IN (SELECT id FROM nutrition WHERE fat = :fat)", nativeQuery = true)
    public List<Object> findMealsByFat(@Param("fat") int fat);

}
