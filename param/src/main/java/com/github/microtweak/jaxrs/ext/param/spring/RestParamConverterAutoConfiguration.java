package com.github.microtweak.jaxrs.ext.param.spring;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestParamConverterAutoConfiguration {

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        RestParamConverterAutoConfiguration.applicationContext = applicationContext;
    }

    public static boolean isAvailable() {
        return applicationContext != null;
    }

    public static List<RestParamConverter<?>> getRestParamConverter() {
        List<RestParamConverter<?>> converters = new ArrayList<>();

        applicationContext.getBeanProvider(RestParamConverter.class).forEach(converters::add);

        return converters;
    }

}
