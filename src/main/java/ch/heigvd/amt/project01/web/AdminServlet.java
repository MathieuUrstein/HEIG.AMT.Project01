package ch.heigvd.amt.project01.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ch.heigvd.amt.project01.util.Utility.PATH;
import static ch.heigvd.amt.project01.util.Utility.disableBrowserCache;

/**
 * Created by sebbos on 02.10.2016.
 */
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        disableBrowserCache(response);
        request.getRequestDispatcher(PATH + "admin.jsp").forward(request, response);
    }
}
