package ru.myastrebov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myastrebov.model.Order;

import java.util.Optional;

/**
 * @author Maxim
 */
public interface OrderRepository extends BaseRepository<Order, Long> {

    @Query("select o FROM Order o JOIN FETCH o.orderedDishList WHERE o.id = (:orderId)")
    Optional<Order> findOrderAndFetchDishesEagerly(@Param("orderId") Long id);
}
