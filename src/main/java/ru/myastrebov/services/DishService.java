package ru.myastrebov.services;

import ru.myastrebov.model.Dish;
import ru.myastrebov.model.DishTag;

import java.util.List;

/**
 * @author Maxim
 */
public interface DishService {
    List<Dish> getMenu();

    Dish getDishById(Long id);

    Dish addNewDish(Dish dish);

    List<DishTag> getAllTags();

    List<Dish> findByTag(Long tagId);
}
