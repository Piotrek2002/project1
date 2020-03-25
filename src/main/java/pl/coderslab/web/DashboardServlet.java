package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "DashboardServlet", value="/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session	=	request.getSession();
        int adminId= (int) session.getAttribute("UserId");
        // Count user recipes
        RecipeDao recipe = new RecipeDao();

        int recipeCount = recipe.countUserRecipes(adminId);
        request.setAttribute("recipeCount",recipeCount);

        // Count user plans
        PlanDao plan = new PlanDao();
        int planCount = plan.countUserPlans(adminId);
        request.setAttribute("planCount",planCount);

        // Get last plan
        ArrayList lastUserPlan = plan.getLastUserPlan(adminId);
        request.setAttribute("lastUserPlan",lastUserPlan);

        // Get last plan name
        String lastUserPlanName = plan.getLastUserPlanName(adminId);
        request.setAttribute("lastUserPlanName",lastUserPlanName);

        getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
