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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet("/app/recipe/list/")
public class AppRecipeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes = recipeDao.findAll();
        Iterator<Recipe> iterator = recipes.listIterator();
        List<Recipe> adminRecipes = new ArrayList<>();
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            HttpSession session	=	request.getSession();
            int adminId= (int) session.getAttribute("UserId");
            if (recipe.getAdminId() == adminId) {
                adminRecipes.add(recipe);
            }
        }
        request.setAttribute("recipes",adminRecipes);
        getServletContext().getRequestDispatcher("/apprecipe.jsp").forward(request, response);
    }
}
