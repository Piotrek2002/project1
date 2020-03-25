package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/app/plan/details/")
public class AppDetailsSchedulesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao=new PlanDao();
        int planid= Integer.parseInt(request.getParameter("id"));
        request.setAttribute("plan_id",planid);
        Plan plan=planDao.read(planid);
        request.setAttribute("plan",plan);

        HttpSession session	=	request.getSession();
        int adminId= (int) session.getAttribute("UserId");

        // Get plan

        ArrayList UserPlan = planDao.getUserPlan(planid);
        request.setAttribute("UserPlan",UserPlan);

        getServletContext().getRequestDispatcher("/app-details-schedules.jsp").forward(request, response);
    }
}
