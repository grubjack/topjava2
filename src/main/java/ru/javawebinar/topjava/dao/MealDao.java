package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Urban Aleksandr on 09.11.2017
 */
public interface MealDao {
    List<Meal> getAll();

    Meal getById(Integer id);

    Integer create(Meal meal);

    void update(Meal meal, Integer id);

    void delete(Integer id);
}
