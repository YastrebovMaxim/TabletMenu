package ru.myastrebov.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myastrebov.dao.DishRepository;
import ru.myastrebov.model.Dish;
import ru.myastrebov.services.DishService;

import java.util.List;
import java.util.Optional;

/**
 * @author Maxim
 */
@Service
@Transactional
public class RestaurantDishService implements DishService {

    private final DishRepository dishRepository;

    @Autowired
    public RestaurantDishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> getMenu() {
        return dishRepository.findAll();
    }

    @Override
    public Dish getDishById(Long id) {
        Optional<Dish> dishOptional = dishRepository.findOne(id);
        return dishOptional.isPresent() ? dishOptional.get() : null;
    }

    @Override
    public Dish addNewDish(Dish dish) {
        return dishRepository.save(dish);
    }
}
