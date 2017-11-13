package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private AdminRestController adminRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        adminRestController = applicationContext.getBean("adminRestController", AdminRestController.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.setAttribute("users", adminRestController.getAll());
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
