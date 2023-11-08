import controller.StarshipController;
import controller.TestController;
//import org.glassfish.appclient.client.acc.Main;
//import org.glassfish.embeddable.GlassFish;
//import org.glassfish.embeddable.GlassFishException;
//import org.glassfish.embeddable.GlassFishProperties;
//import org.glassfish.embeddable.GlassFishRuntime;

import javax.ws.rs.ApplicationPath;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Application;

@ApplicationPath("/api/v1")
public class MyApp extends Application{
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(TestController.class);
        classes.add(StarshipController.class);
        return classes;
    }
    //public static void main(String[] args) {
    //    try {
    //        GlassFishRuntime runtime = GlassFishRuntime.bootstrap();
    //        GlassFishProperties glassfishProperties = new GlassFishProperties();
    //        glassfishProperties.setPort("http-listener", 9080);
    //        glassfishProperties.setPort("https-listener", 9845);
    //        GlassFish glassfish = runtime.newGlassFish(glassfishProperties);
    //        glassfish.start();
    //        glassfish.getDeployer().deploy(new File("opt/applications/clusterjsp.war"));
    //    } catch (GlassFishException ex) {
    //        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    //    }
    //}

}