package ru.myastrebov.model;

import javax.persistence.*;

/**
 * @author Maxim
 */
@Entity
@Table(name = "dish_tag")
public class DishTag {
    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "tag_name", unique = true, nullable = false)
    private String tagName;

    public DishTag() {
    }

    public DishTag(String tagName) {
        this.tagName = tagName;
    }

    public DishTag(String tagName, Long id) {
        this.tagName = tagName;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
