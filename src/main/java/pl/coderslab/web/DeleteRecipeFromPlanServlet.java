package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteRecipeFromPlanServlet")
public class DeleteRecipeFromPlanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int delete = Integer.parseInt(request.getParameter("delete"));
        int plan_id=Integer.parseInt(request.getParameter("plan_id"));
        if (delete==1){

            int recipe_id=Integer.parseInt(request.getParameter("recipe_id"));
            RecipeDao recipeDao=new RecipeDao();
recipeDao.deletefromplan(recipe_id,plan_id);
            response.sendRedirect("/app/plan/details/?id="+plan_id);
        }else {
            response.sendRedirect("/app/plan/details/?id="+plan_id);
        }
    }
}
