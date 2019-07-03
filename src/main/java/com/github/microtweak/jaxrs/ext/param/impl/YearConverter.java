package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.ParameterConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.Year;
import java.util.List;

public class YearConverter implements ParameterConverter<Year> {

    @Override
    public boolean canConvert(Class<Year> rawType, Type genericType, List<Annotation> annotations) {
        return Year.class.isAssignableFrom(rawType);
    }

    @Override
    public Year fromString(String value, Class<Year> rawType, Type genericType, List<Annotation> annotations) {
        return Year.parse(value);
    }

}
