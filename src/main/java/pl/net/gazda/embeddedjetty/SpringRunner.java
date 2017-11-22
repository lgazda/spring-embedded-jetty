package pl.net.gazda.embeddedjetty;

import org.eclipse.jetty.server.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.net.gazda.embeddedjetty.configuration.ApplicationConfiguration;

public class SpringRunner {

	public static void main(String[] args) throws Exception {
		try(AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
			Server jettyServer = applicationContext.getBean(Server.class);
			jettyServer.start();
			jettyServer.join();
		}
	}
}
