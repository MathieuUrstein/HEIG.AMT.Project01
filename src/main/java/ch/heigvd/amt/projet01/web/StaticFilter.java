package ch.heigvd.amt.projet01.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by sebbos on 02.10.2016.
 */
public class StaticFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (isStaticAsset(path)) {
            chain.doFilter(request, response);
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
