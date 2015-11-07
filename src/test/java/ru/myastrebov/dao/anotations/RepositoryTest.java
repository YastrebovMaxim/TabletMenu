package ru.myastrebov.dao.anotations;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ru.myastrebov.dao.config.DaoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Maxim
 */
/*
* Аннотация для того, чтобы контекст загружался для каждого теста новый
* Возникала роблема с непредсказуемостью генерации идентификаторов из-за
* недетерменированости запуска тестов.
* */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
/*Добавляем файлы конфигураций
* если есть повторяющиеся бины в обоих контекстах, берётся бин из контекста, идущего последним,
* в моём случае это dataSource
* */
@ActiveProfiles("dev")
@ContextConfiguration(classes = {DaoConfiguration.class})
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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryTest {
}
