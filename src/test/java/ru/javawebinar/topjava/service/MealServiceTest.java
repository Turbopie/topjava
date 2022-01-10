package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;



@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {


    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(meal6.getId(),USER_ID);
        assertMatch(meal,meal6);
    }

    @Test
    public void getWrongUser() {
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(),ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(meal1.getId(),USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(),USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND,USER_ID));
    }

    @Test
    public void deletedWrongUser() {
        assertThrows(NotFoundException.class, () -> service.delete(meal1.getId(),ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(startDate,endDate,USER_ID);
        assertMatch(all,meal4,meal5,meal6,meal7);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, meal1,meal2,meal3,meal4,meal5,meal6,meal7);
    }

    @Test
    public void update() {
        Meal meal = getUpdatedMeal();
        service.update(meal,USER_ID);
        assertMatch(service.get(meal.getId(),USER_ID),meal);
    }

    @Test
    public void updateWrongUser() {
        assertThrows(NotFoundException.class, () -> service.update(meal1, ADMIN_ID));
        assertMatch(service.get(meal1.getId(), USER_ID), meal1);
    }


    @Test
    public void create() {
        Meal created = service.create(getNewMeal(),USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId,USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Описание", 1000),USER_ID));
    }
}