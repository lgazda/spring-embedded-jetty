package pl.net.gazda.embeddedjetty.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application.properties")
@ComponentScan(basePackages = "pl.net.gazda.embeddedjetty.service")
@Import(JettyServerConfiguration.class)
public class ApplicationConfiguration {

}
