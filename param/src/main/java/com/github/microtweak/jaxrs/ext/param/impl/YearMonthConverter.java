package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.YearMonth;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class YearMonthConverter implements RestParamConverter<YearMonth> {

    @Override
    public YearMonth fromString(String value, Class<YearMonth> rawType, Type genericType, List<Annotation> annotations) {
        return isNotBlank(value) ? YearMonth.parse(value) : null;
    }

}
