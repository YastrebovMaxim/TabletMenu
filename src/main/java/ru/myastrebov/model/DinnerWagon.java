package ru.myastrebov.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Maxim
 */
@Entity
@Table(name = "dinner_wagon")
public class DinnerWagon {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "is_free", nullable = false)
    private boolean isFree;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }
}
