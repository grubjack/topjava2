package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal create(Meal user, int userId);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal user, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getFilteredByDateTime(int userId, LocalDateTime fromDateTime, LocalDateTime toDateTime);

    List<Meal> getFilteredByTime(int userId, LocalTime fromTime, LocalTime toTime);
}