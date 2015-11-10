package ru.myastrebov.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Maxim
 */
@Entity
@Table(name = "ordered_dish")
public class OrderedDish {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false, updatable = false)
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    @Column(name = "start_cooking_time", nullable = false, updatable = false)
    private LocalDateTime startCookingTime;

    @Column(name = "end_cooking_time", updatable = false)
    private LocalDateTime endCookingTime;

    public OrderedDish() {
    }

    public OrderedDish(Dish dish) {
        this.dish = dish;
        startCookingTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public LocalDateTime getStartCookingTime() {
        return startCookingTime;
    }

    public void setStartCookingTime(LocalDateTime startCookingTime) {
        this.startCookingTime = startCookingTime;
    }

    public LocalDateTime getEndCookingTime() {
        return endCookingTime;
    }

    public void setEndCookingTime(LocalDateTime endCookingTime) {
        this.endCookingTime = endCookingTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
