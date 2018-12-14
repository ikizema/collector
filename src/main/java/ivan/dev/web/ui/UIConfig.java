package ivan.dev.web.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.vaadin.server.VaadinServlet;
import ivan.dev.web.ui.views.GlobalUI;

@Configuration
@Profile("ui")
public class UIConfig {
	private static Logger logger = LoggerFactory.getLogger(UIConfig.class);
	
	@Inject
	private HandlerList handlerList;
	
	@PostConstruct
	private void initUI() {
		// Context Handler
        handlerList.addHandler( buildContext() );
        logger.debug("Vaadin UI loaded");
	}
	
    private static ContextHandler buildContext() {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        ServletHolder sh = new ServletHolder(new VaadinServlet());
        contextHandler.addServlet(sh, "/*");
        contextHandler.setInitParameter("ui", GlobalUI.class.getCanonicalName());
        
        return contextHandler;
    }
}
