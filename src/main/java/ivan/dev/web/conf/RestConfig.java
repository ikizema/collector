package ivan.dev.web.conf;

import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import io.swagger.jaxrs.config.BeanConfig;
import ivan.dev.web.rs.RestServices;
import org.springframework.core.Ordered;

@Configuration
@Profile("rest")
@Order(Ordered.LOWEST_PRECEDENCE)
public class RestConfig {
	
	private static Logger logger = LoggerFactory.getLogger(RestConfig.class);
	
	@Value("${rest.service.base.path}")
	private String restservicebasepath;
	
	@Value("${rest.service.relative.swagger.docs}")
	private String swaggerpath;

	@Inject
	private HandlerList handlerList;
	
	@PostConstruct
	private void initRest() throws Exception {
		buildSwagger();
		
        // Build the Swagger Bean.
        handlerList.addHandler( buildSwaggerContext() );
        logger.debug("Swager loaded");
		
        // Build RS Handling.
        handlerList.addHandler( buildRsContext() );
        logger.debug("Rest Services loaded");
	}
	
    private void buildSwagger() {
        // This configures Swagger
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion( "1.0.0" );
        beanConfig.setResourcePackage( RestServices.class.getPackage().getName().concat(".controllers") );
        beanConfig.setScan( true );
        beanConfig.setBasePath( "/" + restservicebasepath + "/api");
        beanConfig.setDescription( "Simple Swagger App." );
        beanConfig.setTitle( "Services Browser" );
    }
    
    private ContextHandler buildSwaggerContext() throws Exception {
        final ResourceHandler swaggerUIResourceHandler = new ResourceHandler();
        swaggerUIResourceHandler.setResourceBase( RestServices.class.getClassLoader().getResource( "swaggerui" ).toURI().toString() );
        final ContextHandler swaggerUIContext = new ContextHandler();
        swaggerUIContext.setContextPath( "/" + restservicebasepath + "/" + swaggerpath + "/" );
        swaggerUIContext.setHandler( swaggerUIResourceHandler );
        return swaggerUIContext;
    }
    
    private ContextHandler buildRsContext() throws URISyntaxException {    	
        // Register and map the dispatcher servlet
        ServletHolder servletHolder = new ServletHolder( new CXFServlet() );
        ServletContextHandler context = new ServletContextHandler();      
      
        context.setContextPath( "/" + restservicebasepath + "/" );
        context.addServlet( servletHolder, "/*" );     
        context.addEventListener( new ContextLoaderListener() );
        
        context.setInitParameter( "contextClass", AnnotationConfigWebApplicationContext.class.getName() );
        context.setInitParameter( "contextConfigLocation", RestServices.class.getName() );  
	    return context;
    }
}
