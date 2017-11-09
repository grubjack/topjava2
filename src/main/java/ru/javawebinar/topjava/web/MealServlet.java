package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.InMemoryMealDaoImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Urban Aleksandr on 08.11.2017
 */
public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealDao mealDao = new InMemoryMealDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if ("edit".equalsIgnoreCase(action)) {
            if (id != null) {
                int mealId = Integer.parseInt(id);
                if (mealId > 0) {
                    log.debug("get meal with id " + mealId);
                    Meal meal = mealDao.getById(mealId);
                    if (meal != null) {
                        request.setAttribute("meal", meal);
                        log.debug("redirect to mealEdit");
                        request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                    }
                }
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            if (id != null) {
                int mealId = Integer.parseInt(id);
                if (mealId > 0) {
                    log.debug("delete meal with id " + mealId);
                    mealDao.delete(mealId);
                }
            }
        }
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        log.debug("redirect to meals");
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String description = request.getParameter("description");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));

        Meal meal = new Meal(dateTime, description, calories);
        if (id != null) {
            log.debug("add new meal");
            mealDao.create(meal);
        } else {
            log.debug("update meal with id " + id);
            mealDao.update(meal, Integer.parseInt(id));
        }
        log.debug("redirect to meals");
        response.sendRedirect("meals");

    }
}
