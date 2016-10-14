package ch.heigvd.amt.project01.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by sebbos on 28.09.2016.
 */
public class SecurityFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

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