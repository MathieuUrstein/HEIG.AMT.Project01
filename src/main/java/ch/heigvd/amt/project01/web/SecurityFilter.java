package ch.heigvd.amt.project01.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ch.heigvd.amt.project01.util.Utility.disableBrowserCache;
import static ch.heigvd.amt.project01.util.Utility.setEncoding;

/**
 * Security filter that check if the user can access to the protected or admin page (he is connected) and do redirection (good JSP)
 * if needed (for example, if the user want to access the protected page but he isn't connected, he will be redirected to
 * the login page).
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        disableBrowserCache(rep);
        setEncoding(req, rep);

        // check if the user is connected or not
        if (req.getSession().getAttribute("userName") != null) {
            // redirection
            if (path.contentEquals("/login") || path.contentEquals("/register")) {
                // keep correct url (the client must do a new request to the ProtectedServlet)
                rep.sendRedirect(req.getContextPath() + "/protected");
            }
            else {
                chain.doFilter(request, response);
            }
        }
        // redirection
        else if (path.contentEquals("/protected") || path.contentEquals("/logout") || path.contentEquals("/admin")) {
            // keep correct url (the client must do a new request to the LoginServlet)
            rep.sendRedirect(req.getContextPath() + "/login");
        }
        else {
            chain.doFilter(request, response);
        }
    }
}
