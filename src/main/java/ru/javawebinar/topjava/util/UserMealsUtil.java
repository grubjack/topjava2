package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> userMealWithExceeds = getFilteredWithExceededByCycle(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        userMealWithExceeds.forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dayCaloriesMap = new HashMap<>();
        List<UserMeal> userMealsByTime = new ArrayList<>();

        for (UserMeal meal : mealList) {
            dayCaloriesMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (v, nv) -> v + nv);
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealsByTime.add(meal);
            }
        }

        List<UserMealWithExceed> mealsWithExceed = new ArrayList<>();
        userMealsByTime.forEach(m -> mealsWithExceed.add(new UserMealWithExceed(m, dayCaloriesMap.get(m.getDateTime().toLocalDate()) > caloriesPerDay)));
        return mealsWithExceed;
    }
}
