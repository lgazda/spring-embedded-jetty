package pl.net.gazda.embeddedjetty.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "pl.net.gazda.embeddedjetty.controller")
public class MvcConfiguration {
}
