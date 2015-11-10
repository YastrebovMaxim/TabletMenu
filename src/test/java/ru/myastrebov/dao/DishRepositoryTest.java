package ru.myastrebov.dao;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.myastrebov.dao.anotations.RepositoryTest;
import ru.myastrebov.model.Dish;
import ru.myastrebov.model.DishTag;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

//TODO поискать другой способ очистки базы данных
@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTest
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/dao/dish/DishRepositoryTestEmptyDataBase.xml")
public class DishRepositoryTest {

    @Autowired
    private DishRepository uut;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/dao/dish/DishRepositoryTestSaveOne.xml")
    public void testSaveOne() throws Exception {
        Dish pizza = prepareDish("pizza", "great pizza", 256L);
        pizza.setTags(Lists.newArrayList(new DishTag("warm"), new DishTag("cheese")));
        Dish savedDish = uut.save(pizza);
        Long id = savedDish.getId();
        assertThat(id, notNullValue());
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/dao/dish/DishRepositoryTestFindAll.xml")
    public void testFindAll() throws Exception {
        List<Dish> allDishes = uut.findAll();
        assertThat(allDishes , notNullValue());
        assertThat(allDishes, hasSize(2));
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/dao/dish/DishRepositoryTestFindAll.xml")
    public void testFindOne() throws Exception {
        long dishId = 1L;
        Optional<Dish> dishOptional = uut.findOne(dishId);
        assertThat(dishOptional.isPresent(), is(true));

        Dish dish = dishOptional.get();
        assertThat(dish.getId(), equalTo(dishId));
        assertThat(dish.getName(), equalTo("tee"));
        assertThat(dish.getDescription(), equalTo("green tee with lemon"));
        assertThat(dish.getCost(), equalTo(125L));
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/dao/dish/DishRepositoryTestFindAll.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/dao/dish/DishRepositoryTestDelete.xml")
    public void testDelete() throws Exception {
        Optional<Dish> dishOptional = uut.findOne(1L);
        assertThat(dishOptional.isPresent(), is(true));

        Dish dish = dishOptional.get();
        uut.delete(dish);
    }

    @Test
    public void testFindDoesNotExistDish() throws Exception {
        Optional<Dish> dishOptional = uut.findOne(1L);
        assertThat(dishOptional.isPresent(), is(false));
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/dao/dish/DishRepositoryTestFindAll.xml")
    public void testFindByTagId() throws Exception {
        List<Dish> dishList = uut.findByTagId(1L);
        assertThat(dishList, notNullValue());
        assertThat(dishList, hasSize(1));
        assertThat(dishList.get(0).getName(), equalTo("tee"));
    }

    @Test
    @DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/dao/dish/DishRepositoryTestFindAll.xml")
    public void testFindByTagName() throws Exception {
        List<Dish> dishList = uut.findByTagName("drinks");
        assertThat(dishList, notNullValue());
        assertThat(dishList, hasSize(1));
    }

    private Dish prepareDish(String name, String description, long cost) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(description);
        dish.setCost(cost);
        return dish;
    }
}