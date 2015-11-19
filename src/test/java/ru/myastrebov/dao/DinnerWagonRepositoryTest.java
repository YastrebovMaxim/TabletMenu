package ru.myastrebov.dao;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.myastrebov.dao.anotations.RepositoryTest;
import ru.myastrebov.model.DinnerWagon;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertTrue;

/**
 * @author Maxim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTest
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/dao/dinnerWagon/DinnerWagonRepositoryTestFindAll.xml")
public class DinnerWagonRepositoryTest {

    @Autowired
    private DinnerWagonRepository uut;

    @Test
    public void testFindAll() throws Exception {
        List<DinnerWagon> dinnerWagonList = uut.findAll();
        assertThat(dinnerWagonList, hasSize(2));
    }

    @Test
    public void testFindOne() throws Exception {
        Optional<DinnerWagon> wagonOptional = uut.findOne(2L);
        assertTrue(wagonOptional.isPresent());

        DinnerWagon dinnerWagon = wagonOptional.get();
        Assert.assertThat(dinnerWagon.getId(), equalTo(2L));
        Assert.assertThat(dinnerWagon.isFree(), equalTo(true));
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "/dao/dinnerWagon/DinnerWagonRepositoryTestDelete.xml")
    public void testDelete() throws Exception {
        Optional<DinnerWagon> wagonOptional = uut.findOne(1L);
        assertTrue(wagonOptional.isPresent());
        uut.delete(wagonOptional.get());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/dao/dinnerWagon/DinnerWagonRepositoryTestSave.xml")
    public void testSaveOne() throws Exception {
        DinnerWagon dinnerWagon = new DinnerWagon();
        dinnerWagon.setFree(true);
        DinnerWagon savedDinnerWagon = uut.save(dinnerWagon);
        Assert.assertThat(savedDinnerWagon.getId(), notNullValue());
    }
}
