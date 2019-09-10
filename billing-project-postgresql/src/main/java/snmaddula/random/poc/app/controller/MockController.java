package snmaddula.random.poc.app.controller;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/mock")
public class MockController {

	private static final Logger logger = Logger.getLogger(MockController.class);
	
	private String externalUrl;
	
	@PostConstruct
	public void init() throws IOException {
		Properties p = new Properties();
		p.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		p.getProperty("external_url");
	}
	
	@RequestMapping("/external")
	public @ResponseBody String callExternal() {
		logger.info("call External");
		RestTemplate rt = new RestTemplate();
		return rt.getForObject(externalUrl, String.class);
	}

	@RequestMapping("/internal")
	public @ResponseBody String callInternal() {
		logger.info("call Internal");
		RestTemplate rt = new RestTemplate();
		return rt.getForObject("http://invoice-internal.com/invoices", String.class);
	}

}
