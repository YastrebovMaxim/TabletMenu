package ru.myastrebov.view.rest.resources;

import org.springframework.hateoas.ResourceSupport;
import ru.myastrebov.model.DishTag;

/**
 * Created by myastrebov on 12/17/15.
 */
public class DishTagResource extends ResourceSupport {
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    DishTag toDishTag() {
        return new DishTag(tagName);
    }

}
