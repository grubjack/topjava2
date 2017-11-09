package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Urban Aleksandr on 09.11.2017
 */
public class InMemoryMealDaoImpl implements MealDao {

    private List<Meal> meals = new CopyOnWriteArrayList<Meal>() {
        {
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        }
    };

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal getById(Integer id) {
        return meals.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Integer create(Meal meal) {
        meals.add(meal);
        return meal.getId();
    }

    @Override
    public void update(Meal meal, Integer id) {
        meal.setId(id);
        Meal oldMeal = getById(id);
        if (oldMeal != null) {
            meals.remove(oldMeal);
            meals.add(meal);
        }
    }

    @Override
    public void delete(Integer id) {
        Meal oldMeal = getById(id);
        if (oldMeal != null) {
            meals.remove(oldMeal);
        }
    }
}
