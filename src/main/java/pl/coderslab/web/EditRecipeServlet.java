package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/EditRecipeServlet")
public class EditRecipeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String preparation_time =request.getParameter("preparation_time");
        String preparation = request.getParameter("preparation");
        String ingredients = request.getParameter("ingredients");
        RecipeDao recipeDao =new RecipeDao();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = formatter.format(date);
        HttpSession session	=	request.getSession();
        int adminId= (int) session.getAttribute("UserId");
        Recipe recipe=new Recipe(name,ingredients,description,dateStr,dateStr,preparation_time,preparation,adminId);
        recipeDao.update(recipe);
        response.sendRedirect("/app/recipe/list/");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recipe_id = Integer.parseInt(request.getParameter("id"));
        RecipeDao recipeDao=new RecipeDao();
        Recipe recipe=recipeDao.read(recipe_id);
        request.setAttribute("recipe",recipe);

        getServletContext().getRequestDispatcher("/app-edit-recipe.jsp").forward(request, response);
    }
}
