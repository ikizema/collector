package ivan.dev.web.rs.conf;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import org.springframework.core.Ordered;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonConfig {
	
	@Bean(name=Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {    	
	   	return new SpringBus();
	}
	
	@Bean
	public JacksonJaxbJsonProvider jsonProvider(){
		return new JacksonJaxbJsonProvider();
	}
	
	@Bean
	public SwaggerSerializers swaggerWriter() {
		return new SwaggerSerializers();
	}
	
	@Bean
	public ApiListingResource swaggerResource(){
		return new ApiListingResource();
	}
	
    @Bean 
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }
}
