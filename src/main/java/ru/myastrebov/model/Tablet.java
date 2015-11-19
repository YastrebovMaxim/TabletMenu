package ru.myastrebov.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Maxim
 */
@Entity
@Table(name = "tablet")
public class Tablet {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "dinner_wagon_id")
    private DinnerWagon dinnerWagon;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DinnerWagon getDinnerWagon() {
        return dinnerWagon;
    }

    public void setDinnerWagon(DinnerWagon dinnerWagon) {
        this.dinnerWagon = dinnerWagon;
    }
}
