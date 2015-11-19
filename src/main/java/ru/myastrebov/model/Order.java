package ru.myastrebov.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order has information about dishes, state, start time and end time.
 *
 * @author Maxim
 */
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "order_comment")
    private String orderComment;

    @Column(name = "order_creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "close_order_time")
    private LocalDateTime closeOrderTime;

    @ManyToOne
    @JoinColumn(name = "dinner_wagon_id", nullable = false)
    private DinnerWagon dinnerWagon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderedDish> orderedDishList;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getCloseOrderTime() {
        return closeOrderTime;
    }

    public void setCloseOrderTime(LocalDateTime closeOrderTime) {
        this.closeOrderTime = closeOrderTime;
    }

    public List<OrderedDish> getOrderedDishList() {
        return orderedDishList;
    }

    public void setOrderedDishList(List<OrderedDish> orderedDishList) {
        this.orderedDishList = orderedDishList;
    }

    public DinnerWagon getDinnerWagon() {
        return dinnerWagon;
    }

    public void setDinnerWagon(DinnerWagon dinnerWagon) {
        this.dinnerWagon = dinnerWagon;
    }
}
