package com.atlassian.springbootreact.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean(name = "objectConverter")
    public ObjectMapper createObjectMapperInstance(){
        return new ObjectMapper();
    }
}
