package ch.heigvd.amt.projet01.web;

import ch.heigvd.amt.projet01.services.UsersManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by sebbos on 28.09.2016.
 */
public class RoutingFilter implements Filter {
    private UsersManager usersManager = null;

    public RoutingFilter() {
        usersManager = new UsersManager();
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setAttribute("usersManager", usersManager);

        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (isStaticAsset(path)) {
            chain.doFilter(request, response);
        }
        else if (path.contentEquals("/")) {
            request.getRequestDispatcher("/front").forward(request, response);
        }
        else if (req.getSession().getAttribute("userName") != null) {
            if (path.contentEquals("/login") || path.contentEquals("/register")) {
                request.getRequestDispatcher("/protected").forward(request, response);
            }
            else {
                request.getRequestDispatcher(path).forward(request, response);
            }
        }
        else if (path.contentEquals("/protected")) {
            request.getRequestDispatcher("/login").forward(request, response);
        }
        else {
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    private boolean isStaticAsset(String path) {
        if (path.startsWith("/css")) {
            return true;
        }
        else if (path.startsWith("/font-awesome")) {
            return true;
        }
        else if (path.startsWith("/fonts")) {
            return true;
        }
        else if (path.startsWith("/img")) {
            return true;
        }
        else if (path.startsWith("/js")) {
            return true;
        }

        return false;
    }
}
