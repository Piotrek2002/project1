package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeletePlanServlet")
public class DeletePlanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int delete = Integer.parseInt(request.getParameter("delete"));
        if (delete==1){
            int id=Integer.parseInt(request.getParameter("id"));
            PlanDao planDao=new PlanDao();
            planDao.deletefromrecipe_plan(id);
            planDao.delete(id);
            response.sendRedirect("/app/plan/list");
        }else {
            response.sendRedirect("/app/plan/list");
        }
    }
}
