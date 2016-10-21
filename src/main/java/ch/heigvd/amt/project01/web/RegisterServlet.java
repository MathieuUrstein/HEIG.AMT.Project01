package ch.heigvd.amt.project01.web;

import ch.heigvd.amt.project01.services.dao.UsersManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.amt.project01.util.Utility.*;

/**
 * Servlet that registers a new user.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class RegisterServlet extends HttpServlet {
    @EJB
    private UsersManagerLocal usersManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // creation of a new user with the given parameters
            usersManager.createUser(request.getParameter("lastName"), request.getParameter("firstName"),
                    request.getParameter("userName"), request.getParameter("password"), request.getParameter("passwordConfirmation"));

            request.getSession().setAttribute("userName", request.getParameter("userName"));
            request.getSession().setMaxInactiveInterval(MAX_SESSION_INACTIVE_INTERVAL);
            // keep correct url (the client must do a new request to the ProtectedServlet)
            response.sendRedirect(request.getContextPath() + "/protected");
        }
        catch (Exception e) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            String message;

            if (e.getCause() != null && e.getCause().getClass().getSimpleName().equals(IllegalArgumentException.class.getSimpleName())) {
                // message for exceptions with the inputs of the client
                message = e.getCause().getMessage();
            }
            else {
                // generic message for other exceptions (the client doesn't need the specific message associated to the exception)
                message = "An error has occurred! Please try again.";
            }

            request.setAttribute("message", message);
            request.setAttribute("maxInputSize", MAX_USER_INPUT_SIZE);
            // keep entries of the user (html form)
            request.setAttribute("givenLastName", request.getParameter("lastName"));
            request.setAttribute("givenFirstName", request.getParameter("firstName"));
            request.setAttribute("givenUserName", request.getParameter("userName"));
            request.setAttribute("givenPassword", request.getParameter("password"));
            request.setAttribute("givenPasswordConfirmation", request.getParameter("passwordConfirmation"));
            request.getRequestDispatcher(PATH + "register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("maxInputSize", MAX_USER_INPUT_SIZE);
        request.getRequestDispatcher(PATH + "register.jsp").forward(request, response);
    }
}
