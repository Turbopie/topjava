package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public class MealRepository implements MealRepositoryInt {

    @Override
    public boolean delete(int mealId) {
        return false;
    }

    @Override
    public boolean save(Meal meal) {
        return false;
    }

    @Override
    public boolean update(Meal meal) {
        return false;
    }

    @Override
    public List<MealTo> getAllMeals() {
        return null;
    }

    @Override
    public Meal getMealById(int mealId) {
        return null;
    }
}
