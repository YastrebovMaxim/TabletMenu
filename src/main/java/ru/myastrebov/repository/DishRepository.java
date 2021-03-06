package ru.myastrebov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myastrebov.model.Dish;
import ru.myastrebov.model.DishTag;

import java.util.List;
import java.util.Optional;

/**
 * @author Maxim
 */
public interface DishRepository extends BaseRepository<Dish, Long> {
    @Query("select d from Dish d join d.tags t where t.id = (:tagId)")
    List<Dish> findByTagId(@Param("tagId") Long tagId);

    @Query("select d from Dish d join d.tags t where t.tagName = (:tagName)")
    List<Dish> findByTagName(@Param("tagName") String tagName);

    @Query("select d FROM Dish d JOIN FETCH d.tags WHERE d.id = (:dishId)")
    Optional<Dish> findOneAndFetchAllLinksEagerly(@Param("dishId") Long id);

}
