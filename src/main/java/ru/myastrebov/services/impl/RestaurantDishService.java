package ru.myastrebov.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.myastrebov.model.DishTag;
import ru.myastrebov.repository.DishRepository;
import ru.myastrebov.model.Dish;
import ru.myastrebov.repository.DishTagRepository;
import ru.myastrebov.services.DishService;
import ru.myastrebov.services.exception.DishDoesNotExistException;
import ru.myastrebov.services.exception.TagDoesNotExistException;

import java.util.List;
import java.util.Optional;

/**
 * @author Maxim
 */
@Service
public class RestaurantDishService implements DishService {

    private final DishRepository dishRepository;
    private final DishTagRepository  dishTagRepository;

    @Autowired
    public RestaurantDishService(DishRepository dishRepository, DishTagRepository dishTagRepository) {
        this.dishRepository = dishRepository;
        this.dishTagRepository = dishTagRepository;
    }

    @Override
    public List<Dish> getMenu() {
        return dishRepository.findAll();
    }

    @Override
    public Dish getDishById(Long id) {
        Optional<Dish> dishOptional = dishRepository.findOne(id);
        if (dishOptional.isPresent()) {
            return dishOptional.get();
        } else {
            throw new DishDoesNotExistException();//todo message
        }
    }

    @Override
    public Dish addNewDish(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public List<DishTag> getAllTags() {
        return dishTagRepository.findAll();
    }

    @Override
    public List<Dish> findByTag(Long tagId) {
        return dishRepository.findByTagId(tagId);
    }
}
