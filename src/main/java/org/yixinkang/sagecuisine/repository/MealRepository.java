package org.yixinkang.sagecuisine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yixinkang.sagecuisine.entity.Meal;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents the repository for Meal entities. It extends
 * JpaRepository
 * and provides additional methods to retrieve Meal entities by name, id, and
 * category name.
 */
@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

    /**
     * Retrieves an Optional containing a Meal entity with the given name, if it
     * exists.
     *
     * @param name the name of the Meal entity to retrieve
     * @return an Optional containing the Meal entity with the given name, if it
     *         exists
     */
    public Optional<Meal> findMealByName(String name);

    /**
     * Retrieves an Optional containing a Meal entity with the given id, if it
     * exists.
     *
     * @param mealId the id of the Meal entity to retrieve
     * @return an Optional containing the Meal entity with the given id, if it
     *         exists
     */
    public Optional<Meal> findMealById(int mealId);

    /**
     * Retrieves a List of Meal entities whose category name contains the given
     * name.
     *
     * @param name the name to search for in the category name of Meal entities
     * @return a List of Meal entities whose category name contains the given name
     */
    @Query(value = "SELECT * FROM Meal WHERE Category LIKE %:name%", nativeQuery = true)
    public List<Meal> findMealByCategoryName(@Param("name") String name);

}
