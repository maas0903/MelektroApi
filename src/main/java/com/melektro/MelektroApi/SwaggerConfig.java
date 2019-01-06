/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melektro.MelektroApi;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
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
@Configuration
public class SwaggerConfig {

    @Autowired(required = false)
    private ServletContext servletContext_;

    @Value("${springfox.documentation.swagger.v2.path}")
    private String swagger2Endpoint;

    @Bean
    public Docket api() {
        System.out.println("docket created for end-point [" + swagger2Endpoint + "]");

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping(swagger2Endpoint)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swaggertest")
                .description("here goes desc")
                .version("1.0")
                .build();
    }
}
