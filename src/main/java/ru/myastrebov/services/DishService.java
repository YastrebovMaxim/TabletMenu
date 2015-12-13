package ru.myastrebov.services;

import ru.myastrebov.model.Dish;

import java.util.List;

/**
 * @author Maxim
 */
public interface DishService {
    List<Dish> getMenu();

    Dish getDishById(Long id);

    Dish addNewDish(Dish dish);
}
