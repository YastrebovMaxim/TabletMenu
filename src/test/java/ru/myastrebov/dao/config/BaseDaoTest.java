package ru.myastrebov.dao.config;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * @author Maxim
 */
/*
* Аннотация для того, чтобы контекст загружался для каждого теста новый
* Возникала роблема с непредсказуемостью генерации идентификаторов из-за
* недетерменированости запуска тестов.
* */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
/*Используем spring-загрузчик классов, чтобы можно было использовать аннотации от Spring*/
@RunWith(SpringJUnit4ClassRunner.class)
/*Добавляем файлы конфигураций
* если есть повторяющиеся бины в обоих контекстах, берётся бин из контекста, идущего последним,
* в моём случае это dataSource
* */

@ContextHierarchy({
        @ContextConfiguration(classes = {DaoConfiguration.class, TestDaoConfiguration.class})
})
/*
* Список "слушатель", обрабатывающих запуск теста, включаются при использовании
* определённых аннтоцай
* */
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public abstract class BaseDaoTest {
}
