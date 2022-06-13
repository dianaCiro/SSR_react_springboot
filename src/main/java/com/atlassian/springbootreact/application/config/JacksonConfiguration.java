package com.atlassian.springbootreact.application.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Primary;

/**
 * Class to setting date format serialization.
 * @author diana.ciro
 *
 */
@Configuration
public class JacksonConfiguration {
    public static final String FORMATO_LOCAL_DATE_TIME = "yyyy-MM-dd HH:mm";

    /**
     * Serializes and deserializes localDateTime in ISO 8601 format.
     * @return ObjectMapper instance.
     */
    @Bean
    @Primary
    public ObjectMapper dateMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        SimpleModule module = new SimpleModule();

        module.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
                gen.writeString(DateTimeFormatter.ofPattern(FORMATO_LOCAL_DATE_TIME).format(localDateTime));
            }
        });

        module.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                return LocalDateTime.parse(jp.getText(), DateTimeFormatter.ofPattern(FORMATO_LOCAL_DATE_TIME));
            }
        });

        objectMapper.registerModule(javaTimeModule);

        objectMapper.registerModule(module);
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper;
    }
}