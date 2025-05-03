package com.odiga.global.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Asia/Seoul"));
            jacksonObjectMapperBuilder.simpleDateFormat(DATE_TIME_FORMAT);
            jacksonObjectMapperBuilder.serializers(
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)),
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
            );
            jacksonObjectMapperBuilder.deserializers(
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)),
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
            );
        };
    }
}
