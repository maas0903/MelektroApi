package com.melektro.MelektroApi;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@ComponentScan(basePackageClasses = {Greeting.class})
@Component
public class MelektroApiApplication {

	public static void main(String[] args) {
            SpringApplication app = new SpringApplication(MelektroApiApplication.class);
            app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
            app.run(args);
		//SpringApplication.run(MelektroApiApplication.class, args);
	}
}

