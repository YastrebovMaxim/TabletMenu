package ru.myastrebov.view.rest.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import ru.myastrebov.model.DishTag;
import ru.myastrebov.view.controllers.DishController;
import ru.myastrebov.view.rest.resources.DishTagResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by myastrebov on 12/19/15.
 */
public class DishTagAsm extends ResourceAssemblerSupport<DishTag, DishTagResource>{

    public DishTagAsm() {
        super(DishTag.class, DishTagResource.class);
    }

    @Override
    public DishTagResource toResource(DishTag entity) {
        DishTagResource resource = new DishTagResource();
        resource.setTagName(entity.getTagName());
        resource.add(linkTo(methodOn(DishController.class).getDishesByTag(entity.getId())).withSelfRel());
        return resource;
    }
}
