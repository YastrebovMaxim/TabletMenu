package ru.myastrebov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.myastrebov.dao.DishRepository;
import ru.myastrebov.model.Dish;

import java.util.List;

/**
 * @author Maxim
 */
@Controller
public class StartPageController {

    @Autowired
    private DishRepository dishRepository;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String homePage() {
        List<Dish> dishList = dishRepository.findAll();
        System.out.println(dishList);
        return "index";
    }
}
