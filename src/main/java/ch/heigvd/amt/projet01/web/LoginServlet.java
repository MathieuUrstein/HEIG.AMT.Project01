package ch.heigvd.amt.projet01.web;

import ch.heigvd.amt.projet01.services.UsersManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sebbos on 01.10.2016.
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsersManager usersManager = (UsersManager) request.getAttribute("usersManager");

        if (usersManager.checkLoginOK(request.getParameter("userName"), request.getParameter("userPassword"))) {
            System.out.println("USER CONNECTED");
            request.getSession().setAttribute("userName", request.getParameter("userName"));
            // setting session to expiry in 30 mins
            request.getSession().setMaxInactiveInterval(30 * 60);
            request.getRequestDispatcher("/protected").forward(request, response);
        }
        else {
            System.out.println("USER NOT CONNECTED");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}
