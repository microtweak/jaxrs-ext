package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class JavaSqlDateConverter implements RestParamConverter<Date> {

    @Override
    public Date fromString(String value, Class<Date> rawType, Type genericType, List<Annotation> annotations) {
        return isNotBlank(value) ? java.sql.Date.valueOf(value) : null;
    }

}
