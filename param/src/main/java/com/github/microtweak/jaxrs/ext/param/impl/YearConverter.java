package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.Year;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class YearConverter implements RestParamConverter<Year> {

    @Override
    public Year fromString(String value, Class<Year> rawType, Type genericType, List<Annotation> annotations) {
        return isNotBlank(value) ? Year.parse(value) : null;
    }

}
