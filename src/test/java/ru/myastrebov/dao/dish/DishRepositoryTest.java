package ru.myastrebov.dao.dish;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.myastrebov.dao.config.BaseDaoTest;
import ru.myastrebov.model.Dish;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

//TODO поискать другой способ очистки базы данных
@DatabaseSetup(
        type = DatabaseOperation.CLEAN_INSERT,
        value = "/dao/dish/DishRepositoryTestEmptyDataBase.xml"
)
public class DishRepositoryTest extends BaseDaoTest {

    @Autowired
    private DishRepository uut;

    @Test
    @ExpectedDatabase(
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "/dao/dish/DishRepositoryTestSaveOne.xml"
    )
    public void testSaveOne() throws Exception {
        Dish savedDish = uut.save(prepareDish("pizza", "great pizza", 256L));
        Long id = savedDish.getId();
        assertThat(id, notNullValue());
    }

    @Test
    @DatabaseSetup(
            type = DatabaseOperation.CLEAN_INSERT,
            value = "/dao/dish/DishRepositoryTestFindAll.xml"
    )
    public void testFindAll() throws Exception {
        List<Dish> allDishes = uut.findAll();
        assertThat(allDishes , notNullValue());
        assertThat(allDishes, hasSize(2));
    }

    @Test
    @DatabaseSetup(
            type = DatabaseOperation.CLEAN_INSERT,
            value = "/dao/dish/DishRepositoryTestFindAll.xml"
    )
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
    @DatabaseSetup(
            type = DatabaseOperation.CLEAN_INSERT,
            value = "/dao/dish/DishRepositoryTestFindAll.xml"
    )
    @ExpectedDatabase(
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "/dao/dish/DishRepositoryTestDelete.xml"
    )
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

    private Dish prepareDish(String name, String description, long cost) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(description);
        dish.setCost(cost);
        return dish;
    }
}