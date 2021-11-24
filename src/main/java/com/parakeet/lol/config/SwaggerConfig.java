package com.parakeet.lol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(
                DocumentationType.SWAGGER_2
        ).apiInfo(apiInfo())
                .tags(
                        new Tag("Student", "Endpoints for CRUD operations on StudentController"),
                        new Tag("University", "Endpoints for CRUD operations on UniversityController")
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.parakeet.lol"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Locale.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .genericModelSubstitutes(Optional.class)
                .securitySchemes(Collections.singletonList(basicAuthScheme()))
                .securityContexts(Collections.singletonList(securityContext()));

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Fiatwise API")
                .description("Backend task")
                .version("1.0.0")
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(Collections.singletonList(basicAuthReference())).build();
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }
}
