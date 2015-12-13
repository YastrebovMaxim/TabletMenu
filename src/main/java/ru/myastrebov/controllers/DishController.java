package ru.myastrebov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.myastrebov.model.Dish;
import ru.myastrebov.services.DishService;

import java.util.List;

/**
 * @author Maxim
 */
@RestController
@RequestMapping(value = "/dish")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @RequestMapping(value = {"/all", ""}, method = RequestMethod.GET, produces = "application/json")
    public List<Dish> getAllDishes() {
        return dishService.getMenu();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Dish getDish(@PathVariable("id") Long id) {
        Dish dish = dishService.getDishById(id);
        return dish != null ? dish : null;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public Dish addDish(@RequestBody Dish dish) {
        return dishService.addNewDish(dish);
    }
}
