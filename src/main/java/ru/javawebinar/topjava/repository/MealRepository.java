package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal, int userId);

    void delete(int id, int userId);

    Meal get(int id, int userId);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getFilteredByDateTime(int userId, LocalDateTime fromDateTime, LocalDateTime toDateTime);

    Collection<Meal> getFilteredByTime(int userId, LocalTime fromTime, LocalTime toTime);
}
