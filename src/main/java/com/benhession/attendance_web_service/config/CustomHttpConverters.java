package com.benhession.attendance_web_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class CustomHttpConverters {

    @Bean
    public HttpMessageConverter<BufferedImage> bufferedImageConverter() {
        BufferedImageHttpMessageConverter converter = new BufferedImageHttpMessageConverter();
        converter.setDefaultContentType(MediaType.IMAGE_PNG);

        return converter;
    }
}
