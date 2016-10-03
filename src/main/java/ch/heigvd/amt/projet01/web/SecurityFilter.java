package ch.heigvd.amt.projet01.web;

import ch.heigvd.amt.projet01.services.UsersManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by sebbos on 28.09.2016.
 */
public class SecurityFilter implements Filter {
    private UsersManager usersManager = null;

    public SecurityFilter() {
        usersManager = new UsersManager();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setAttribute("usersManager", usersManager);

        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // problème quand on revient en arrière dans le navigateur (on peut quand même revenir sur la page de login alors qu'on
        // est connecté) => bloquer le cache du navigateur

        if (req.getSession().getAttribute("userName") != null) {
            if (path.contentEquals("/login") || path.contentEquals("/register")) {
                request.getRequestDispatcher("/protected").forward(request, response);
            }
            else {
                chain.doFilter(request, response);
            }
        }
        else if (path.contentEquals("/protected") || path.contentEquals("/logout")) {
            request.getRequestDispatcher("/login").forward(request, response);
        }
        else {
            chain.doFilter(request, response);
        }
    }
}
