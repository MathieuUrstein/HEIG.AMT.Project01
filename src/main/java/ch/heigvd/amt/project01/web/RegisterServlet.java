package ch.heigvd.amt.project01.web;

import ch.heigvd.amt.project01.services.UsersManagerLocal;
import ch.heigvd.amt.project01.util.Utility;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sebbos on 01.10.2016.
 */
public class RegisterServlet extends HttpServlet {
    @EJB
    private UsersManagerLocal usersManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // vérifier qu'on a bien un email correctemnt formé (@ + .domaine) => méthode java surement

        // peut être un problème avec les messages d'erreur retournés par le serveur (genre 200 au lieu de 404 par exemple)

        // eviter de tout effacer si une erreur (amélioration)

        // problème quand on revient en arrière dans le navigateur (on peut quand même revenir sur la page de login alors qu'on
        // est connecté) => bloquer le cache du navigateur surement

        // vérifier que le changement de l'url fonctionne meme en entrant une url a la main (genre /login) + sans a la main

        // problème d'encodage dans la bd (é => ?!@¦)

        try {
            usersManager.createUser(request.getParameter("lastName"), request.getParameter("firstName"),
                    request.getParameter("userName"), request.getParameter("password"), request.getParameter("passwordConfirmation"));

            System.out.println("USER REGISTERED");

            request.getSession().setAttribute("userName", request.getParameter("userName"));
            // setting session to expiry in 30 min
            request.getSession().setMaxInactiveInterval(30 * 60);
            request.getRequestDispatcher("/protected").forward(request, response);
        }
        catch (Exception e) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, e.getMessage(), e);

            String message;

            if (e.getCause().getClass().getSimpleName().equals("IllegalArgumentException")) {
                // message for exceptions with the inputs of the client
                message = e.getCause().getMessage();
            }
            else {
                // generic message for other exception (the client don't need the specific message associated to the exception)
                message = "An error has occurred! Please try again.";
            }

            request.setAttribute("message", message);
            //response.sendRedirect(request.getContextPath() + "/register");
            request.getRequestDispatcher(Utility.PATH + "register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Utility.PATH + "register.jsp").forward(request, response);
    }
}
