package snmaddula.random.poc.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import snmaddula.random.poc.app.config.AppContext;

public class App implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext c = new AnnotationConfigWebApplicationContext();
		c.register(AppContext.class);
		Dynamic dispatcher = servletContext.addServlet("dispatcherServlet", new DispatcherServlet(c));
		dispatcher.addMapping("/");
		dispatcher.setLoadOnStartup(1);
	}
}
