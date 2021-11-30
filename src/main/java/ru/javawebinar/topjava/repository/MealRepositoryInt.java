package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealRepositoryInt {

    boolean delete(int mealId);

    boolean save(Meal meal);

    boolean update(Meal meal);

    List<MealTo> getAllMeals();

    Meal getMealById(int mealId);

}
