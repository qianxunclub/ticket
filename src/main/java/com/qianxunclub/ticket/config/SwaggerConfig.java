package com.qianxunclub.ticket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lombok.Data;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author zhangbin
 * @date 2019-07-19 10:57
 * @description: TODO
 */
@Configuration
@EnableSwagger2
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig extends WebMvcConfigurerAdapter {
    private Boolean enabled;

    private String title;

    private String description;

    private String webBasePackage;

    private String author;

    private String url;

    private String email;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(this.getEnabled())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.getWebBasePackage()))
                .paths(not(regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        String author = this.getAuthor();
        String url = this.getUrl();
        String email = this.getEmail();
        return new ApiInfoBuilder()
                .title(this.getTitle())
                .description(this.getDescription())
                .contact(new Contact(author, url, email))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
