package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebFilter(urlPatterns = "/RecipesServlet")
public class FilterLoginToApp implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest	httpRequest	=	(HttpServletRequest)	req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        HttpSession session = ((HttpServletRequest) req).getSession();

        System.out.println(httpRequest.getSession().getAttribute("User"));

        if (session.getAttribute("User") == null) {
            RecipeDao recipeDao=new RecipeDao();
            List<Recipe> list=recipeDao.findAll();
            httpRequest.setAttribute("recipes",list);
            ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/recipes.jsp"); // Not logged in, redirect to login page.
        } else {
//            ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/apprecipe.jsp"); // Not logged in, redirect to login page.
            chain.doFilter(req, resp); // Logged in, just continue chain.
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
