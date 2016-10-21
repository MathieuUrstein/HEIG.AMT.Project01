package ch.heigvd.amt.project01.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that is used for specific serialisation's configurations for DTOs (JSON).
 * It is also our entry point of our REST API (.../api/...).
 *
 * @author Mathieu Urstein and Sébastien Boson
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
