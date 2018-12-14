package ivan.dev.web;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import ivan.dev.web.cli.RuntimeConfig;
import ivan.dev.web.server.AppConfig;

public class Main {
	
    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
    	// Bridge Util Logging
    	SLF4JBridgeHandler.removeHandlersForRootLogger();
    	SLF4JBridgeHandler.install();
    	
    	AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		ConfigurableEnvironment env = context.getEnvironment();
		MutablePropertySources propertySources = env.getPropertySources();
		
        // Parse Command Line Arguments and to Spring Context
        RuntimeConfig runtimeConfig = new RuntimeConfig();
		CmdLineParser parser = new CmdLineParser(runtimeConfig);
		try {
			parser.parseArgument(args);
	    } catch( CmdLineException e ) {
	        System.err.println(e.getMessage());
	        parser.printUsage(System.err);
	        return;
	    }
		
		// Display Help Message
		if (runtimeConfig.isHelp()) {
			System.out.println("Application help page :");
			parser.printUsage(System.out);
			return;
		}
		
		propertySources.addFirst(new MapPropertySource("cli", runtimeConfig.getProperties()));
		
		// Add Global Properties
		String envGlobalProperties = "classpath:config/env/env-global.properties";
		ResourcePropertySource envGlobalPropertiesRessources = new ResourcePropertySource("envGlobalProperties", envGlobalProperties);
		propertySources.addAfter("cli", envGlobalPropertiesRessources);

		// Add Environment Related Properties
		String envProperties = "classpath:config/env/env-${env}.properties";
		envProperties = envProperties.replace("${env}", env.getProperty("env"));
		ResourcePropertySource envPropertiesRessources = new ResourcePropertySource("envProperties", envProperties);
		propertySources.addBefore("envGlobalProperties", envPropertiesRessources);
		
		context.register(AppConfig.class);
		context.refresh();
        LOGGER.debug("Spring Context loaded.");
    }
}
