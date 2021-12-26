package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAllFiltered(@Nullable LocalDate dateMin, @Nullable LocalDate dateMax,
                                       @Nullable LocalTime timeMin, @Nullable LocalTime timeMax) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll using userId: {} with filter dates between {} and {}, time between {} and {}",
                userId,dateMin,dateMax,timeMin,timeMax);

        return MealsUtil.getFilteredTos(service.getAllFiltered(userId,dateMin,dateMax), DEFAULT_CALORIES_PER_DAY,timeMin,timeMax);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll using userId: {}", userId);
        return MealsUtil.getTos(service.getAll(userId),DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get {} using userId: {}", id, userId);
        return service.get(id,userId);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {} by userId: {}", meal, userId);
        checkNew(meal);
        return service.create(meal,userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} with userId={}", meal, userId);
        assureIdConsistent(meal, id);
        service.update(meal,userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {} with userId: {}", id,userId);
        service.delete(id,userId);
    }


}