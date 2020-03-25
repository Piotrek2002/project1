package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteRecipeServlet")
public class DeleteRecipeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int delete = Integer.parseInt(request.getParameter("delete"));

        if (delete==1){
            int id=Integer.parseInt(request.getParameter("id"));
            RecipeDao recipeDao=new RecipeDao();
            recipeDao.delete(id);
            response.sendRedirect("/app/recipe/list/");
        }else {
            response.sendRedirect("/app/recipe/list/");
        }
    }
}
