
package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository mealRepository = new MealRepository();
    private final static String INSERT_OR_EDIT = "/mealEdit.jsp";
    private final static String LIST_MEAL = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String forward;
        String action = request.getParameter("action");


        if(action == null) {
            log.debug("redirect to meals");
            forward = LIST_MEAL;
            request.setAttribute("meals", mealRepository.getAllMeals());
        } else if (action.equalsIgnoreCase("delete")) {
            log.debug("redirect to meals delete");
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealRepository.delete(mealId);
            forward = LIST_MEAL;
            request.setAttribute("meals", mealRepository.getAllMeals());
        } else if (action.equalsIgnoreCase("edit")) {
            log.debug("redirect to meals insert or edit");
            forward = INSERT_OR_EDIT;
            if(request.getParameter("mealId") == null) {
                request.setAttribute("meal", new Meal(null,null,0));
            } else {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = mealRepository.getMealById(mealId);
                request.setAttribute("meal", meal);
            }
        } else {
            log.debug("redirect to meals insert or edit");
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        String dateTimeStr = req.getParameter("dateTime").replace("T"," ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

        Meal meal = new Meal(dateTime,description,calories);

        String mealIdStr = req.getParameter("mealId");

        if(mealIdStr == null || mealIdStr.isEmpty())
        {
            log.debug("redirect to meals save");
        }
        else
        {
            log.debug("redirect to meals update");
            meal.setId(Integer.parseInt(mealIdStr));
        }

        mealRepository.save(meal);

        RequestDispatcher view = req.getRequestDispatcher(LIST_MEAL);
        req.setAttribute("meals", mealRepository.getAllMeals());
        view.forward(req, resp);
    }



       // List<MealTo> mealsTo = MealsUtil.filteredByStreams(MealsUtil.meals);
       //request.setAttribute("meals",mealsTo);
       //request.getRequestDispatcher("/meals.jsp").forward(request, response);
        //response.sendRedirect("meals.jsp");
}