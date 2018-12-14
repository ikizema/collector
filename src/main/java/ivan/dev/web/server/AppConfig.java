package ivan.dev.web.server;

import javax.inject.Inject;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

@Configuration
@ComponentScan({"ivan.dev.web.conf", "ivan.dev.web.ui"})
public class AppConfig {
	
	private static Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
	
	@Value("${port}")
	private int port;
	
	@Inject
	private HandlerList handlerList;
	
	@Bean
	public HandlerList handlerList() {
		return new HandlerList();
	}
	
	@Bean
	public Server server() {
        try {
            Resource.setDefaultUseCaches( false );
            // Start server
            Server server = new Server(port);
            // Register and map the dispatcher servlet
            final ServletHolder servletHolder = new ServletHolder( new CXFServlet() );
            final ServletContextHandler context = new ServletContextHandler();         
            context.setContextPath( "/" );
            context.addServlet( servletHolder, "/rs/*" );     
            context.addEventListener( new ContextLoaderListener() );
            
//            context.setInitParameter( "contextClass", AnnotationConfigWebApplicationContext.class.getName() );
//            context.setInitParameter( "contextConfigLocation", RestServices.class.getName() );
            
            server.setHandler( handlerList );
            server.start();            
            server.join();
            return server;
        } catch ( Exception e ) {
        	LOGGER.error( "There was an error starting up the Entity Browser", e );
        }
		return null;
	}
}
