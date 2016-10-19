package ch.heigvd.amt.project01.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used to do the logout of an user.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // the session is ended
        request.getSession(false).invalidate();

        // keep correct url (the client must do a new request to the FrontServlet)
        response.sendRedirect(request.getContextPath() + "/");
    }
}
