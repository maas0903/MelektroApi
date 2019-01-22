/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

//import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author marius
 */
@Component
@Order(1)
@EnableSwagger2
@PropertySource({"classpath:swagger.properties"})
@ConditionalOnResource(resources = {"classpath:swagger.properties"})

// mtn 
@Configuration
// according to https://stackoverflow.com/questions/41718459/spring-boot-swagger-2-configuration-error-creating-bean-with-name-documentation
//@Import(SwaggerConfig.class)

public class SwaggerConfig {

    @Autowired(required = false)
    //private ServletContext servletContext_;

    @Value("${springfox.documentation.swagger.v2.path}")
    private String swagger2Endpoint;

    @Bean
    public Docket api() {
        System.out.println("docket created for end-point [" + swagger2Endpoint + "]");
        //System.out.println("docket created for end-point [/]");

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping(swagger2Endpoint)
                //.pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("MelektroApi")
                .description("Various APIs at www.melektro.eu")
                .version("1.0")
                .build();
        
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
            webServerFactoryCustomizer() {
        return factory -> factory.setContextPath("/MelektroApi");
    }

}
