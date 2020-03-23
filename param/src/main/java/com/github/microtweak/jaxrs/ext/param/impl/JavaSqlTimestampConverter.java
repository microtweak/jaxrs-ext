package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class JavaSqlTimestampConverter implements RestParamConverter<Timestamp> {

    @Override
    public Timestamp fromString(String value, Class<Timestamp> rawType, Type genericType, List<Annotation> annotations) {
        return isNotBlank(value) ? Timestamp.valueOf(value) : null;
    }

}
