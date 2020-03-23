package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class LocalDateConverter implements RestParamConverter<LocalDate> {

    @Override
    public LocalDate fromString(String value, Class<LocalDate> rawType, Type genericType, List<Annotation> annotations) {
        return isNotBlank(value) ? LocalDate.parse(value) : null;
    }

}
