package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.ParameterConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

public class LocalDateTimeConverter implements ParameterConverter<LocalDateTime> {

    @Override
    public boolean canConvert(Class<LocalDateTime> rawType, Type genericType, List<Annotation> annotations) {
        return LocalDateTime.class.isAssignableFrom(rawType);
    }

    @Override
    public LocalDateTime fromString(String value, Class<LocalDateTime> rawType, Type genericType, List<Annotation> annotations) {
        return LocalDateTime.parse(value);
    }

}
