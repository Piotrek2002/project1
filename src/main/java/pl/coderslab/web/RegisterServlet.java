package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String first_name = request.getParameter("name");
        String last_name = request.getParameter("surname");
        String email = request.getParameter("email");
        if (request.getParameter("password").equals(request.getParameter("repassword"))) {
            String password = request.getParameter("password");
            Admin admin = new Admin(first_name, last_name, email, password, 0, 0);
            AdminDao adminDao=new AdminDao();
            adminDao.create(admin);
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
        else {
            getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
