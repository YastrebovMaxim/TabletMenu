package ru.myastrebov.repository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.annotation.ExpectedDatabases;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.myastrebov.repository.anotations.RepositoryTest;
import ru.myastrebov.model.DinnerWagon;
import ru.myastrebov.model.Dish;
import ru.myastrebov.model.Order;
import ru.myastrebov.model.OrderedDish;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Maxim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTest
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = {
        "/dao/dish/DishRepositoryTestFindAll.xml"
})
public class OrderRepositoryTest {
    private final LocalDateTime orderCreationTime = LocalDateTime.of(2015, 11, 23, 10, 56, 43);
    @Autowired
    private OrderRepository uut;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DinnerWagonRepository dinnerWagonRepository;

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = "/dao/order/OrderRepositoryTestFindAll.xml")
    public void testFindAll() throws Exception {
        List<Order> orderList = uut.findAll();
        assertThat(orderList, hasSize(1));
        Order order = orderList.get(0);
        assertThat(order.getOrderComment(), equalTo("green tee with lemon"));
        assertThat(order.getCreationTime(), equalTo(orderCreationTime));//2015-11-23 10:56:43
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = "/dao/dinnerWagon/DinnerWagonRepositoryTestFindAll.xml")
    @ExpectedDatabases(value = {
            @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/dao/order/OrderRepositoryTestSaveOne.xml"),
            @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/dao/dish/DishRepositoryTestFindAll.xml"),
            @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/dao/dinnerWagon/DinnerWagonRepositoryTestFindAll.xml")
    })
    public void testSaveOne() throws Exception {
        Optional<Dish> dishOptional = dishRepository.findOne(1L);
        assertTrue(dishOptional.isPresent());

        Optional<DinnerWagon> dinnerWagonOptional = dinnerWagonRepository.findOne(1L);
        assertTrue(dinnerWagonOptional.isPresent());

        Order order = new Order();
        order.setCreationTime(orderCreationTime);
        order.setOrderComment("comment");
        order.setDinnerWagon(dinnerWagonOptional.get());

        OrderedDish orderedDish = new OrderedDish(dishOptional.get());
        orderedDish.setOrder(order);

        order.setOrderedDishList(Lists.newArrayList(orderedDish));
        Order savedOrder = uut.save(order);
        assertThat(savedOrder, is(notNullValue()));
        assertThat(savedOrder.getId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = "/dao/order/OrderRepositoryTestFindAll.xml")
    public void testFindOne() throws Exception {
        Optional<Order> orderOptional = uut.findOne(1L);
        assertTrue(orderOptional.isPresent());
        assertThat(orderOptional.get(), is(notNullValue()));

        Order order = orderOptional.get();
        assertThat(order.getId(), equalTo(1L));
        assertThat(order.getOrderComment(), equalTo("green tee with lemon"));
        assertThat(order.getCreationTime(), equalTo(orderCreationTime));
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = "/dao/order/OrderRepositoryTestFindAll.xml")
    @ExpectedDatabases(value = {
            @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/dao/order/OrderRepositoryTestDeleteOne.xml"),
            @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/dao/dish/DishRepositoryTestFindAll.xml")
    })
    public void testDelete() throws Exception {
        Optional<Order> orderOptional = uut.findOne(1L);
        Order order = orderOptional.get();
        assertThat(order, is(notNullValue()));

        uut.delete(order);
    }

    @Test(expected = LazyInitializationException.class)
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = "/dao/order/OrderRepositoryTestFindAll.xml")
    public void testLazyInitializationException() throws Exception {
        Optional<Order> orderOptional = uut.findOne(1L);
        assertTrue(orderOptional.isPresent());
        orderOptional.get().getOrderedDishList().size();
        assertFalse("This line wil not perform", true);
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.INSERT, value = "/dao/order/OrderRepositoryTestFindAll.xml")
    public void testFindOrderByIdAndFetchDishesEarly() throws Exception {
        Optional<Order> orderOptional = uut.findOrderAndFetchDishesEagerly(1L);
        assertTrue(orderOptional.isPresent());
        Assert.assertThat(orderOptional.get().getOrderedDishList(), Matchers.hasSize(2));
    }
}
