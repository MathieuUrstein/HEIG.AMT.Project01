package ch.heigvd.amt.project01.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sebbos on 15.10.2016.
 */
@ApplicationPath("/api")
public class Project01 extends Application {
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();

        // configuration for GlassFish
        properties.put("jersey.config.disableMoxyJson", true);
        return properties;
    }
}
