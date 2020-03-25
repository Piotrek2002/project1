package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;
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
import java.util.ListIterator;

@WebServlet("/app/plan/list")
public class AppSchedulesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao=new PlanDao();
        List<Plan> plans=planDao.findAll();
        Iterator<Plan> iterator=plans.listIterator();
        List<Plan> adminPlans=new ArrayList<>();
        while (iterator.hasNext()){
            Plan plan = iterator.next();
            HttpSession session	=	request.getSession();
            int adminId= (int) session.getAttribute("UserId");
            if (plan.getAdminId() == adminId) {
                adminPlans.add(plan);
            }
        }
        request.setAttribute("plans",adminPlans);
        getServletContext().getRequestDispatcher("/app-schedules.jsp").forward(request, response);

    }
}
