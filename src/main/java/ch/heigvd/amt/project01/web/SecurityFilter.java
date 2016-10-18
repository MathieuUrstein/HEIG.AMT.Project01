package ch.heigvd.amt.project01.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ch.heigvd.amt.project01.util.Utility.setEncoding;

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
        HttpServletResponse rep = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        setEncoding(req, rep);

        if (req.getSession().getAttribute("userName") != null) {
            if (path.contentEquals("/login") || path.contentEquals("/register")) {
                // keep correct url (the client must do a new request to the ProtectedServlet)
                rep.sendRedirect(req.getContextPath() + "/protected");
            }
            else {
                chain.doFilter(request, response);
            }
        }
        else if (path.contentEquals("/protected") || path.contentEquals("/logout") || path.contentEquals("/admin")) {
            // keep correct url (the client must do a new request to the LoginServlet)
            rep.sendRedirect(req.getContextPath() + "/login");
        }
        else {
            chain.doFilter(request, response);
        }
    }
}
