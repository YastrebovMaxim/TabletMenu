package ru.myastrebov.view.rest.resources;

import org.springframework.hateoas.ResourceSupport;
import ru.myastrebov.model.Dish;

import javax.persistence.Column;

/**
 * todo
 * Created by myastrebov on 12/17/15.
 */
public class DishResource extends ResourceSupport {

    private String name;

    private String description;

    private Long cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Dish toDish() {
        Dish dish = new Dish();
        dish.setCost(cost);
        dish.setName(name);
        dish.setDescription(description);
        return dish;
    }
}
