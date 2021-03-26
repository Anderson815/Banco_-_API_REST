package com.anderson.banco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket bancoApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.anderson.banco"))
                .paths(regex("/conta.*"))
                .build()
                .apiInfo(metaInfo());
    }

    public ApiInfo metaInfo(){

        Contact contato = new springfox.documentation.service.Contact("Anderson", "https://www.linkedin.com/in/anderson-correia", "anderson_souza33@outlook.com.br");

        return new ApiInfoBuilder()
                .title("Banco API REST")
                .description("API REST de uma instituição financeira")
                .version("1.0")
                .license("MIT License")
                .licenseUrl("https://github.com/Anderson815/Banco_-_API_REST/blob/master/LICENSE")
                .contact(contato)
                .build();
    }
}
