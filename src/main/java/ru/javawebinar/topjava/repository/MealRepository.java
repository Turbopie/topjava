package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepository implements MealRepositoryInt {

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }


    @Override
    public boolean delete(int mealId) {
        return repository.remove(mealId) != null;
    }

    @Override
    public boolean save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
        }
        return repository.put(meal.getId(),meal) != null;
    }

    @Override
    public boolean update(Meal meal) {
        return repository.put(meal.getId(), meal) != null;
    }

    @Override
    public List<MealTo> getAllMeals() {
        List<Meal> mealList = new ArrayList<>(repository.values());
        return MealsUtil.filteredByStreams(mealList);
    }

    @Override
    public Meal getMealById(int mealId) {
        return repository.get(mealId);
    }
}
