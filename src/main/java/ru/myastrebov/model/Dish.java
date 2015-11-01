package ru.myastrebov.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

/**
 * @author Maxim
 */
@Entity
@Table(name = "dish")
public class Dish {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", columnDefinition = "Название блюда")
    private String name;

    @Column(name = "description", columnDefinition = "Описание блюда", length = 1024)
    private String description;

    @Column(name = "cost", columnDefinition = "Стоимость блюда")
    private Long cost;
    //private List<Image> photos;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "tag_to_dish",
            joinColumns = {@JoinColumn(name = "dish_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false)}
    )
    private List<DishTag> tags;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DishTag> getTags() {
        return tags;
    }

    public void setTags(List<DishTag> tags) {
        this.tags = tags;
    }
}
