package ch.heigvd.amt.project01.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ch.heigvd.amt.project01.util.Utility.PATH;

/**
 * Created by sebbos on 02.10.2016.
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "The user " + request.getSession(false).getAttribute("userName") + " was correctly disconnected.";

        request.setAttribute("message", message);
        request.getSession(false).invalidate();

        System.out.println("USER DISCONNECTED");

        request.getRequestDispatcher(PATH + "index.jsp").forward(request, response);
    }
}
