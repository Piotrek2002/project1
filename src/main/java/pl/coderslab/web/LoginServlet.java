package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AdminDao adminDao = new AdminDao();
        boolean loginOk = adminDao.login(password, email);
        if (loginOk) {

            HttpSession session	=	request.getSession();

            session.setAttribute("User", email);
            session.setAttribute("UserId", adminDao.adminId(email));
            Admin admin=adminDao.read(adminDao.adminId(email));
            session.setAttribute("name",admin.getFirst_name());
            Cookie name = new Cookie("name",admin.getFirst_name() );
            response.addCookie(name);
            //session.setMaxInactiveInterval(100);

            getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);

        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
