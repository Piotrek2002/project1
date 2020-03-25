package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddPlanServlet", value = "/app/recipe/plan/add")
public class AddRecipeToPlanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String plan = request.getParameter("plan");
        String recipeName = request.getParameter("recipeName");
        String recipeNumber = request.getParameter("recipeNumber");
        String recipe = request.getParameter("recipe");
        String day = request.getParameter("day");
        PlanDao planDao = new PlanDao();
        int id = planDao.createPlanRecipe(recipe, recipeName, recipeNumber, day, plan);
        response.sendRedirect("/app/recipe/plan/add");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao plan = new PlanDao();
        List<Plan> plans = plan.findAll();
        request.setAttribute("plans",plans);

        RecipeDao recipe = new RecipeDao();
        List<Recipe> recipes = recipe.findAll();
        request.setAttribute("recipes",recipes);

        DayNameDao day = new DayNameDao();
        List<DayName> days = day.findAll();
        request.setAttribute("days",days);

        getServletContext().getRequestDispatcher("/app-add-schedules.jsp").forward(request, response);

    }
}
