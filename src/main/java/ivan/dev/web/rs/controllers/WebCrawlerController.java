package ivan.dev.web.rs.controllers;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ivan.dev.web.rs.service.WebCrawlerService;

@Api (value = "Web Crawler Controller")
@Controller
@Path("/crawler")
@Produces("application/json")
public class WebCrawlerController {
	
	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(WebCrawlerController.class);
	
	@Inject
	private WebCrawlerService webCrawlerService;
	
    @GET
    @Path("/test")
    @ApiOperation(value = "Test.")
    @Produces(MediaType.APPLICATION_JSON)
    public void test(@QueryParam("url") String url)  {
    	webCrawlerService.testHtmlParsing(url);
    }

}
