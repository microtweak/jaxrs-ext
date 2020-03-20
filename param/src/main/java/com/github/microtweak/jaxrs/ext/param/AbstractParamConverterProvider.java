package com.github.microtweak.jaxrs.ext.param;

import lombok.extern.java.Log;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Log
public abstract class AbstractParamConverterProvider implements ParamConverterProvider {

    protected abstract List<RestParamConverter> getConverters();

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        final List<Annotation> annotationList = Arrays.asList( annotations );

        return getConverters().stream()
                .filter(c -> c.canConvert(rawType, genericType, annotationList))
                .findFirst()
                .map(c -> toJaxRsConverter(c, rawType, genericType, annotationList))
                .orElse(null);
    }

    private <T> ParamConverter<T> toJaxRsConverter(RestParamConverter<T> converter, Class<T> rawType, Type genericType, List<Annotation> annotations) {
        return new ParamConverter<T>() {
            @Override
            public T fromString(String value) {
                return converter.fromString(value, rawType, genericType, annotations);
            }

            @Override
            public String toString(T value) {
                return converter.toString(value);
            }
        };
    }

}
