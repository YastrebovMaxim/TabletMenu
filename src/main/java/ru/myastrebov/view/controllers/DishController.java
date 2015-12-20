package ru.myastrebov.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.myastrebov.model.Dish;
import ru.myastrebov.model.DishTag;
import ru.myastrebov.services.DishService;
import ru.myastrebov.services.exception.DishDoesNotExistException;
import ru.myastrebov.view.exception.NotFoundException;
import ru.myastrebov.view.rest.asm.DishResourceAsm;
import ru.myastrebov.view.rest.asm.DishTagAsm;
import ru.myastrebov.view.rest.resources.DishResource;
import ru.myastrebov.view.rest.resources.DishTagResource;

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

    @RequestMapping(value = {"/all"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<DishResource>> getAllDishes() {
        return ResponseEntity.ok(new DishResourceAsm().toResources(dishService.getMenu()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<DishResource> getDish(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(new DishResourceAsm().toResource(dishService.getDishById(id)));
        } catch (DishDoesNotExistException e) {
            throw new NotFoundException(e);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity addDish(@RequestBody Dish dish) {
        return ResponseEntity.ok(new DishResourceAsm().toResource(dishService.addNewDish(dish)));
    }

    @RequestMapping(value = "/tag/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<DishTagResource>> getAllTags() {
        List<DishTag> allTags = dishService.getAllTags();
        return ResponseEntity.ok(new DishTagAsm().toResources(allTags));
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<DishResource>> getDishesByTag(@PathVariable("tagId") Long tagId) {
        return ResponseEntity.ok(new DishResourceAsm().toResources(dishService.findByTag(tagId)));
    }
}
