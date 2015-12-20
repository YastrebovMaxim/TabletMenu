package ru.myastrebov.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.myastrebov.repository.DishRepository;

/**
 * @author Maxim
 */
@Controller
public class StartPageController {

    @Autowired
    private DishRepository dishRepository;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String homePage() {
        return "index";
    }
}
