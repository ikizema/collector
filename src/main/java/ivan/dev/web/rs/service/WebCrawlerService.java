package ivan.dev.web.rs.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WebCrawlerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebCrawlerService.class);
	
	public Map<String, String> extractData(String url) {
		
		Map<String, String> data = new HashMap<String, String>();
		
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			LOGGER.warn("Not able to parse URL: {} Exception: {} Cause: {}", url, e.getMessage(), e.getCause());
			return data;
		}
		LOGGER.info("Title: {}", doc.title());
		Elements newsHeadlines = doc.select("#mp-itn b a");
		for (Element headline : newsHeadlines) {
			LOGGER.info("{}	{}", headline.attr("title"), headline.absUrl("href"));
		}
		
		return data;
	}
}
