package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.ParameterConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class DateConverter implements ParameterConverter<Date> {

    @Override
    public boolean canConvert(Class<Date> rawType, Type genericType, List<Annotation> annotations) {
        return Date.class.isAssignableFrom(rawType);
    }

    @Override
    public Date fromString(String value, Class<Date> rawType, Type genericType, List<Annotation> annotations) {
        if (java.sql.Date.class.isAssignableFrom(rawType)) {
            return java.sql.Date.valueOf(value);
        }

        else if (java.sql.Time.class.isAssignableFrom(rawType)) {
            return java.sql.Time.valueOf(value);
        }

        else if (java.sql.Timestamp.class.isAssignableFrom(rawType)) {
            return java.sql.Timestamp.valueOf(value);
        }

        long time = java.sql.Timestamp.valueOf(value).getTime();
        return new Date(time);
    }

}
