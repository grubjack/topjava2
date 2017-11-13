package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("getAll by user with id={}", AuthorizedUser.id());
        return service.getAll(AuthorizedUser.id());
    }

    public Meal get(int id) {
        log.info("get {} by user with id={}", id, AuthorizedUser.id());
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        log.info("create {} by user with id={}", meal, AuthorizedUser.id());
        checkNew(meal);
        return service.create(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        log.info("delete {} by user with id={}", id, AuthorizedUser.id());
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={} by user with id={}", meal, id, AuthorizedUser.id());
        assureIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }
}