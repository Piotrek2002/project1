package pl.coderslab.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/VerificationRecipeFromPlanServlet")
public class VerificationRecipeFromPlanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("plan_id",Integer.parseInt(request.getParameter("plan_id")));
        request.setAttribute("recipe_id",Integer.parseInt(request.getParameter("recipe_id")));
        getServletContext().getRequestDispatcher("/recipefromplanverification.jsp").forward(request, response);
    }
}
