import controller.StarshipController;
import controller.TestController;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

@ApplicationPath("/api/v1")
public class MyApp extends Application{
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(TestController.class);
        classes.add(StarshipController.class);
        return classes;
    }
}