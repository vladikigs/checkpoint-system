package com.vladislav.bashirov.checkpoint.system.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.DatabaseStartupValidator;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.stream.Stream;

@Configuration
@ConfigurationProperties(prefix = "db.reconnect")
public class MyConfiguration {

    @Getter
    @Setter
    private int timeout;
    @Getter
    @Setter
    private int interval;

    @Bean
    public DatabaseStartupValidator databaseStartupValidator(DataSource dataSource) {
        DatabaseStartupValidator dsv = new DatabaseStartupValidator();
        dsv.setDataSource(dataSource);
        dsv.setTimeout(timeout);
        dsv.setInterval(interval);
        return dsv;
    }

    @Bean
    public static BeanFactoryPostProcessor dependsOnPostProcessor() {
        return beanFactory -> {
            String[] jpa = beanFactory.getBeanNamesForType(EntityManagerFactory.class);
            Stream.of(jpa)
                    .map(beanFactory::getBeanDefinition)
                    .forEach(it -> it.setDependsOn("databaseStartupValidator"));
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer () {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}
