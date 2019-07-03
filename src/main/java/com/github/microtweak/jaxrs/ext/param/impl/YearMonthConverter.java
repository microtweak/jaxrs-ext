package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.ParameterConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.YearMonth;
import java.util.List;

public class YearMonthConverter implements ParameterConverter<YearMonth> {

    @Override
    public boolean canConvert(Class<YearMonth> rawType, Type genericType, List<Annotation> annotations) {
        return YearMonth.class.isAssignableFrom(rawType);
    }

    @Override
    public YearMonth fromString(String value, Class<YearMonth> rawType, Type genericType, List<Annotation> annotations) {
        return YearMonth.parse(value);
    }
}
