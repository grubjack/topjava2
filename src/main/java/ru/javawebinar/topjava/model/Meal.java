package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.UsersUtil.DEFAULT_USER_ID;

public class Meal extends AbstractBaseEntity {

    private final int userId;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(int userId, LocalDateTime dateTime, String description, int calories) {
        this(userId, null, dateTime, description, calories);
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(DEFAULT_USER_ID, null, dateTime, description, calories);
    }

    public Meal(int userId, Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.userId = userId;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public int getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "userId=" + userId +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", id=" + id +
                "} " + super.toString();
    }


}
