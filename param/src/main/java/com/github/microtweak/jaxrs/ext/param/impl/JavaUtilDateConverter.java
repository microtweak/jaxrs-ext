package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class JavaUtilDateConverter implements RestParamConverter<Date> {

    @Override
    public Date fromString(String value, Class<Date> rawType, Type genericType, List<Annotation> annotations) {
        if (isNotBlank(value)) {
            long time = java.sql.Timestamp.valueOf(value).getTime();
            return new Date(time);
        }
        return null;
    }

}
