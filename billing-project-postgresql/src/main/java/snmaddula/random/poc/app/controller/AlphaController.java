package snmaddula.random.poc.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import snmaddula.random.poc.app.entity.AlphaEntity;
import snmaddula.random.poc.app.repo.AlphaRepo;

@Controller
@RequestMapping("/alpha")
@SuppressWarnings("serial")
public class AlphaController {
	
	private static final Logger logger = Logger.getLogger(AlphaController.class);

	private static final String FILE_PATH = "C:/Users/narays38/Desktop/namelist.txt";

	@Autowired
	private AlphaRepo alphaRepo;
	
	@PostConstruct
	public void init() throws IOException {
		logger.info("Init");
		File f = new File(FILE_PATH);
		if (!f.exists())
			f.createNewFile();
	}

	@RequestMapping("/view")
	public ModelAndView showView(HttpServletRequest req) {
		logger.info("showView");
		List<String> names = new ArrayList<>();
		Enumeration<String> attrs = req.getSession().getAttributeNames();
		while(attrs.hasMoreElements()) names.add(attrs.nextElement());
		return new ModelAndView("alpha", new HashMap<String, List<String>>() {{
			put("names", names);
		}});
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String postName(@RequestBody AlphaEntity alpha, HttpServletRequest req) throws IOException {
		final String name = alpha.getName();
		logger.info("postMessage: " + name);
		req.getSession().setAttribute(name.toLowerCase(), name.toUpperCase());
		Files.write(Paths.get(FILE_PATH), (name + "\n").getBytes(), StandardOpenOption.APPEND);
		alphaRepo.save(alpha);
		return "DONE";
	}

	
}
