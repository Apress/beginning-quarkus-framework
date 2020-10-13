package org.acme.bean.spring.config;

import org.acme.bean.spring.AnotherSpringBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean(name = "anotherSpringBean")
    public AnotherSpringBean anotherSpringBean() {
        return new AnotherSpringBean();
    }
}
