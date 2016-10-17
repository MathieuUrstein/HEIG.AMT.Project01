package ch.heigvd.amt.project01.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static void requestEncoding(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
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

    public static String toJSON(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(object);
    }
}
