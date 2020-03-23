package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class LocalDateTimeConverter implements RestParamConverter<LocalDateTime> {

    @Override
    public LocalDateTime fromString(String value, Class<LocalDateTime> rawType, Type genericType, List<Annotation> annotations) {
        return isNotBlank(value) ? LocalDateTime.parse(value) : null;
    }

}
