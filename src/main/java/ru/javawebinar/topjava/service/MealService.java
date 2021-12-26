package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.javawebinar.topjava.util.ValidationUtil.*;


@Service
public class MealService {

private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }




    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getAllFiltered(int userId, LocalDate dateMin, LocalDate dateMax) {
        return repository.getAllFiltered(userId, atStartOfDayOrMin(dateMin), atStartOfNextDayOrMax(dateMax));
    }

    public Meal get(int id,int userId) {
        return checkNotFoundWithId(repository.get(id,userId),userId);
    }

    public Meal create(Meal meal, int userId) {
       checkNew(meal);
        return repository.save(meal,userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal,userId),userId);
    }

    public void delete(int id,int userId) {
        checkNotFoundWithId(repository.delete(id,userId),userId);
    }




}