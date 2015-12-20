package ru.myastrebov.view.rest.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.util.CollectionUtils;
import ru.myastrebov.model.Dish;
import ru.myastrebov.model.DishTag;
import ru.myastrebov.view.controllers.DishController;
import ru.myastrebov.view.rest.resources.DishResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by myastrebov on 12/20/15.
 */
public class DishResourceAsm extends ResourceAssemblerSupport<Dish, DishResource>{

    public DishResourceAsm() {
        super(DishController.class, DishResource.class);
    }

    @Override
    public DishResource toResource(Dish entity) {
        DishResource resource = new DishResource();
        resource.setCost(entity.getCost());
        resource.setName(entity.getName());
        resource.setDescription(entity.getDescription());
        resource.add(linkTo(methodOn(DishController.class).getDish(entity.getId())).withSelfRel());
        if (!CollectionUtils.isEmpty(entity.getTags())) {
            for (DishTag dishTag : entity.getTags()) {
                resource.add(linkTo(methodOn(DishController.class).getDishesByTag(dishTag.getId())).withRel("tag"));
            }
        }
        return resource;
    }
}
