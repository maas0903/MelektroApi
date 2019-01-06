package com.melektro.MelektroApi;

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
		SpringApplication.run(MelektroApiApplication.class, args);
	}

}

