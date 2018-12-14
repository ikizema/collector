package ivan.dev.web.rs;

import java.util.Arrays;

import javax.inject.Inject;
import javax.ws.rs.ext.RuntimeDelegate;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.apache.cxf.jaxrs.validation.ValidationExceptionMapper;
import org.apache.cxf.message.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import ivan.dev.web.rs.conf.JaxRsApiApplication;
import ivan.dev.web.rs.controllers.WebCrawlerController;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
@ComponentScan({"ivan.dev.web.rs"})
public class RestServices {
	@Inject
	private ApiListingResource swaggerResource;
	
	@Inject
	private SwaggerSerializers swaggerWriter;
	
	@Inject
	private JacksonJaxbJsonProvider jsonProvider;
	
	@Inject
	private JaxRsApiApplication jaxRsApiApplication;
	
	@Inject
	private WebCrawlerController webCrawlerController;
	
    @Bean @DependsOn( "cxf" )
    public Server jaxRsServer() {
        JAXRSServerFactoryBean factory = 
                RuntimeDelegate.getInstance().createEndpoint( 
                    jaxRsApiApplication, 
                    JAXRSServerFactoryBean.class 
                );
            factory.setServiceBean(swaggerResource);
            factory.setServiceBean(webCrawlerController);
            factory.setAddress( factory.getAddress() );
            factory.setInInterceptors( 
                Arrays.< Interceptor< ? extends Message > >asList( 
                    new JAXRSBeanValidationInInterceptor()
                ) 
            );
            factory.setOutInterceptors( 
                Arrays.< Interceptor< ? extends Message > >asList( 
                    new JAXRSBeanValidationOutInterceptor() 
                ) 
            );
            factory.setProviders( 
                Arrays.asList( 
                    new ValidationExceptionMapper(),
                    swaggerWriter,
                    jsonProvider
                ) 
            );
        return factory.create();
    }
}
