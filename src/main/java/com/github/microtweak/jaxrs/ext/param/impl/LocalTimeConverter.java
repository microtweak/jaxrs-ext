package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.ParameterConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.List;

public class LocalTimeConverter implements ParameterConverter<LocalTime> {

    @Override
    public boolean canConvert(Class<LocalTime> rawType, Type genericType, List<Annotation> annotations) {
        return LocalTime.class.isAssignableFrom(rawType);
    }

    @Override
    public LocalTime fromString(String value, Class<LocalTime> rawType, Type genericType, List<Annotation> annotations) {
        return LocalTime.parse(value);
    }

}
