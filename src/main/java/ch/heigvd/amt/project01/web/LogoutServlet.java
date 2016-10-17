package ch.heigvd.amt.project01.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sebbos on 02.10.2016.
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(false).invalidate();

        System.out.println("USER DISCONNECTED");

        // keep correct url (the client must do a new request to the FrontServlet)
        response.sendRedirect(request.getContextPath() + "/");
    }
}
