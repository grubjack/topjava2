package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.util.ValidationUtil.checkWrongId;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal create(Meal meal, int userId) throws NotFoundException {
        checkWrongId(meal.getUserId(), userId);
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(get(id, userId), id);
        repository.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        checkWrongId(meal.getUserId(), userId);
        repository.save(meal, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return new ArrayList<>(repository.getAll(userId));
    }

    @Override
    public List<Meal> getFilteredByDateTime(int userId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return new ArrayList<>(repository.getFilteredByDateTime(userId, fromDateTime, toDateTime));
    }

    @Override
    public List<Meal> getFilteredByTime(int userId, LocalTime fromTime, LocalTime toTime) {
        return new ArrayList<>(repository.getFilteredByTime(userId, fromTime, toTime));
    }
}
