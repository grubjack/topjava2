package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.UsersUtil.DEFAULT_USER_ID;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(m, DEFAULT_USER_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        if (meal.getUserId() == userId) {
            return repository.put(meal.getId(), meal);
        }
        return null;
    }

    @Override
    public void delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId() == userId) {
            repository.remove(id);
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId() == userId) {
            return meal;
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().filter(m -> m.getUserId() == userId).sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getFilteredByDateTime(int userId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return getAll(userId)
                .stream()
                .filter(m -> m.getUserId() == userId).filter(m -> DateTimeUtil.isBetween(m.getDateTime(), fromDateTime, toDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getFilteredByTime(int userId, LocalTime fromTime, LocalTime toTime) {
        return getAll(userId)
                .stream()
                .filter(m -> m.getUserId() == userId).filter(m -> DateTimeUtil.isBetween(m.getDateTime().toLocalTime(), fromTime, toTime))
                .collect(Collectors.toList());
    }
}

