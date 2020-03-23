package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class LocalTimeConverter implements RestParamConverter<LocalTime> {

    @Override
    public LocalTime fromString(String value, Class<LocalTime> rawType, Type genericType, List<Annotation> annotations) {
        return isNotBlank(value) ? LocalTime.parse(value) : null;
    }

}
