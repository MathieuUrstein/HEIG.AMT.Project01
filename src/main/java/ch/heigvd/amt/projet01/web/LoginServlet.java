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

        // problème form vide => vérification

        // problème si on remplit les champs de la page login et on appuie sur le lien register il fait un post sur la page register direct
        // PEUT ETRE PAS EN FAIT => A TESTER (VRAIMENT NON EN FAITE)

        // ENCORE A FAIRE
        /**
         * 1. empecher l'utilisateur d'envoyer un formulaire vide (sans nom d'utilisateur et mot de passe) cote client DONE
         *    + peut etre faire un check cote serveur => A FAIRE
         * 2. renvoyer des messages d'erreur quand l'utilisateur se connecte ou s'enregistre
         * 3. renvoyer un message d'information pour dire à l'utilisateur qu'il s'est bien déconnecté
         * 4. texte plus parlant pour la page d'accueil
         */

        if (usersManager.checkLoginOK(request.getParameter("userName"), request.getParameter("userPassword"))) {
            System.out.println("USER CONNECTED");
            request.getSession().setAttribute("userName", request.getParameter("userName"));
            // setting session to expiry in 30 mins
            request.getSession().setMaxInactiveInterval(30 * 60);
            request.getRequestDispatcher("/protected").forward(request, response);
        }
        else {
            System.out.println("USER NOT EXISTING");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}
