package ch.heigvd.amt.project01.util;

import javax.servlet.http.HttpServletRequest;
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
}
