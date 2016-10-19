package ch.heigvd.amt.project01.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sebbos on 12.10.2016.
 */
public class Utility {
    public static final String PATH = "/WEB-INF/pages/";
    // setting session to expiry in 30 mins
    public static final int MAX_SESSION_INACTIVE_INTERVAL = 30 * 60;
    // max size in characters
    public static final int MAX_USER_ENTRY_SIZE = 60;
    private static final String ENCODING = "UTF-8";

    public static void setEncoding(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding(ENCODING);
            response.setCharacterEncoding(ENCODING);
        }
        catch (UnsupportedEncodingException e) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void disableBrowserCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // proxies
    }
}
