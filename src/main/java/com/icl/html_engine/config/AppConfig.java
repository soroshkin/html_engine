package com.icl.html_engine.config;

import com.icl.html_engine.service.Person;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    @ConfigurationProperties(prefix = "person")
    public Person getPerson(){
        return new Person();
    }
}
