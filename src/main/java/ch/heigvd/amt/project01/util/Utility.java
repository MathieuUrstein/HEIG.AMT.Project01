package ch.heigvd.amt.project01.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class that is used to define some constants and needed methods (in the project).
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class Utility {
    /**
     * base path to JSP
     */
    public static final String PATH = "/WEB-INF/pages/";
    /**
     * setting session to expiry in 30 m
     */
    public static final int MAX_SESSION_INACTIVE_INTERVAL = 30 * 60;
    /**
     * max size in characters
     */
    public static final int MAX_USER_INPUT_SIZE = 60;
    private static final String ENCODING = "UTF-8";

    /**
     * Set the encoding of a request and a response (to avoid encoding problems).
     *
     * @param request the request that we want to specify the encoding
     * @param response the response that we want to specify the encoding
     */
    public static void setEncoding(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding(ENCODING);
            response.setCharacterEncoding(ENCODING);
        }
        catch (UnsupportedEncodingException e) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Disable the cache of the browser by adding specific headers.
     *
     * @param response the response in which we want to add the headers
     */
    public static void disableBrowserCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // proxies
    }
}
