package ru.myastrebov.repository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.myastrebov.repository.anotations.RepositoryTest;
import ru.myastrebov.model.Tablet;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Maxim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@RepositoryTest
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/dao/tablet/TabletRepositoryTestFindAll.xml")
public class TabletRepositoryTest {

    @Autowired
    private TabletRepository uut;

    @Test
    public void testFindAll() throws Exception {
        List<Tablet> tabletList = uut.findAll();
        assertThat(tabletList, hasSize(2));
    }

    @Test
    public void testFindOne() throws Exception {
        Optional<Tablet> tabletOptional = uut.findOne(1L);
        assertTrue(tabletOptional.isPresent());

        Tablet tablet = tabletOptional.get();
        assertThat(tablet.getDinnerWagon().getId(), equalTo(2L));
        assertThat(tablet.getDinnerWagon().isFree(), equalTo(true));
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/dao/tablet/TabletRepositoryTestDelete.xml")
    public void testDelete() throws Exception {
        Optional<Tablet> tabletOptional = uut.findOne(1L);
        assertTrue(tabletOptional.isPresent());

        uut.delete(tabletOptional.get());

        assertFalse(uut.findOne(1L).isPresent());
    }


}
