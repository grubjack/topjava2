package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        log.info("getAll by user with id={}", AuthorizedUser.getId());
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.getId()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFilteredByDateTime(String fromDate, String fromTime, String toDate, String toTime) {
        LocalDate fromLocalDateTime = fromDate.isEmpty() ? LocalDate.MIN : DateTimeUtil.toLocalDate(fromDate);
        LocalDate toLocalDateTime = toDate.isEmpty() ? LocalDate.MAX : DateTimeUtil.toLocalDate(toDate);
        LocalTime fromLocalTime = fromTime.isEmpty() ? LocalTime.MIN : DateTimeUtil.toLocalTime(fromTime);
        LocalTime toLocalTime = toTime.isEmpty() ? LocalTime.MAX : DateTimeUtil.toLocalTime(toTime);
        LocalDateTime fromDateTime = LocalDateTime.of(fromLocalDateTime, fromLocalTime);
        LocalDateTime toDateTime = LocalDateTime.of(toLocalDateTime, toLocalTime);
        log.info("getFiltered by user with id={} from date {} to date", AuthorizedUser.getId(), fromDateTime, toDateTime);
        return MealsUtil.getWithExceeded(service.getFilteredByDateTime(AuthorizedUser.getId(), fromDateTime, toDateTime), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFilteredByTime(String fromTime, String toTime) {
        LocalTime fromLocalTime = fromTime.isEmpty() ? LocalTime.MIN : DateTimeUtil.toLocalTime(fromTime);
        LocalTime toLocalTime = toTime.isEmpty() ? LocalTime.MAX : DateTimeUtil.toLocalTime(toTime);
        log.info("getFiltered by user with id={} from time {} to time", AuthorizedUser.getId(), fromTime, toTime);
        return MealsUtil.getWithExceeded(service.getFilteredByTime(AuthorizedUser.getId(), fromLocalTime, toLocalTime), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {} by user with id={}", id, AuthorizedUser.getId());
        return service.get(id, AuthorizedUser.getId());
    }

    public Meal create(Meal meal) {
        log.info("create {} by user with id={}", meal, AuthorizedUser.getId());
        checkNew(meal);
        return service.create(meal, AuthorizedUser.getId());
    }

    public void delete(int id) {
        log.info("delete {} by user with id={}", id, AuthorizedUser.getId());
        service.delete(id, AuthorizedUser.getId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={} by user with id={}", meal, id, AuthorizedUser.getId());
        assureIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.getId());
    }
}