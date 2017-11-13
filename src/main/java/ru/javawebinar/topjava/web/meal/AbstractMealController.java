package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll(int userId) {
        log.info("getAll by user with id={}", userId);
        return service.getAll(userId);
    }

    public Meal get(int id, int userId) {
        log.info("get {} by user with id={}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal, int userId) {
        log.info("create {} by user with id={}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {} by user with id={}", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id, int userId) {
        log.info("update {} with id={} by user with id={}", meal, id, userId);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }
}