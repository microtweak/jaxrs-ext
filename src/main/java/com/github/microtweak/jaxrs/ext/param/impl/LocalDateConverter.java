package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.ParameterConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public class LocalDateConverter implements ParameterConverter<LocalDate> {

    @Override
    public boolean canConvert(Class<LocalDate> rawType, Type genericType, List<Annotation> annotations) {
        return LocalDate.class.isAssignableFrom(rawType);
    }

    @Override
    public LocalDate fromString(String value, Class<LocalDate> rawType, Type genericType, List<Annotation> annotations) {
        return LocalDate.parse(value);
    }

}
