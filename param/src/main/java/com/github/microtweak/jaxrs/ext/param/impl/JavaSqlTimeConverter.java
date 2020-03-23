package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class JavaSqlTimeConverter implements RestParamConverter<Time> {

    @Override
    public Time fromString(String value, Class<Time> rawType, Type genericType, List<Annotation> annotations) {
        return isNotBlank(value) ? Time.valueOf(value) : null;
    }

}
