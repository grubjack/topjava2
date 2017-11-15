package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = applicationContext.getBean("mealRestController", MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        if (id == null) {
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String fromTime = request.getParameter("fromTime");
            String toTime = request.getParameter("toTime");


            LocalTime fromLocalTime = fromTime.isEmpty() ? LocalTime.MIN : DateTimeUtil.toLocalTime(fromTime);
            LocalTime toLocalTime = toTime.isEmpty() ? LocalTime.MAX : DateTimeUtil.toLocalTime(toTime);

            List<MealWithExceed> filteredMeal;
            if (fromDate.isEmpty() && toDate.isEmpty()) {
                filteredMeal = MealsUtil.getWithExceeded(mealRestController.getAll(AuthorizedUser.getId()), MealsUtil.DEFAULT_CALORIES_PER_DAY)
                        .stream()
                        .filter(m -> DateTimeUtil.isBetween(m.getDateTime().toLocalTime(), fromLocalTime, toLocalTime))
                        .collect(Collectors.toList());
            } else {
                LocalDate fromLocalDate = fromDate.isEmpty() ? LocalDate.MIN : DateTimeUtil.toLocalDate(fromDate);
                LocalDate toLocalDate = toDate.isEmpty() ? LocalDate.MAX : DateTimeUtil.toLocalDate(toDate);
                filteredMeal = MealsUtil.getWithExceeded(mealRestController.getAll(AuthorizedUser.getId()), MealsUtil.DEFAULT_CALORIES_PER_DAY)
                        .stream()
                        .filter(m -> DateTimeUtil.isBetween(m.getDateTime(), LocalDateTime.of(fromLocalDate, fromLocalTime), LocalDateTime.of(toLocalDate, toLocalTime)))
                        .collect(Collectors.toList());
            }
            request.setAttribute("meals", filteredMeal);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")),
                    AuthorizedUser.getId());
            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if (meal.isNew()) {
                mealRestController.create(meal, AuthorizedUser.getId());
            } else {
                mealRestController.update(meal, Integer.valueOf(id), AuthorizedUser.getId());
            }
            response.sendRedirect("meals");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id, AuthorizedUser.getId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request), AuthorizedUser.getId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(mealRestController.getAll(AuthorizedUser.getId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
