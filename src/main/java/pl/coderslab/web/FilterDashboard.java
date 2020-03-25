package pl.coderslab.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/DashboardServlet")
public class FilterDashboard implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest	=	(HttpServletRequest)	req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        HttpSession session = ((HttpServletRequest) req).getSession();
        System.out.println(httpRequest.getSession().getAttribute("User"));

        if (session.getAttribute("User") == null) {
            ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/LoginServlet"); // Not logged in, redirect to login page.
        } else {

            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
