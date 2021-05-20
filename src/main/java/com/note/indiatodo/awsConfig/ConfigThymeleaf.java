package com.note.indiatodo.awsConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ConfigThymeleaf{
@Bean
public ClassLoaderTemplateResolver secondaryTemplateResolver() {
        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
        secondaryTemplateResolver.setPrefix("templates/");
        secondaryTemplateResolver.setSuffix(".html");
        secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
        secondaryTemplateResolver.setCharacterEncoding("UTF-8");
        secondaryTemplateResolver.setOrder(1);
        secondaryTemplateResolver.setCheckExistence(true);
        return secondaryTemplateResolver;
        }
        @Bean
        SpringResourceTemplateResolver xmlTemplateResolver() {
                SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
                templateResolver.setPrefix("classpath:/static/assets/svgFiles");
                templateResolver.setSuffix(".svg");
                templateResolver.setTemplateMode("XML");
                templateResolver.setCharacterEncoding("UTF-8");
                templateResolver.setCacheable(true);
                return templateResolver;
        }


}