package com.github.microtweak.jaxrs.ext.param;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class RestParamConverterProvider implements ParamConverterProvider {

    private List<RestParamConverter> converters;

    public List<RestParamConverter> getConverters() {
        if (converters == null) {
            final Spliterator<ConverterLookup> iterator = ServiceLoader.load(ConverterLookup.class).spliterator();

            converters = StreamSupport.stream(iterator, false)
                    .filter(ConverterLookup::isAvailable)
                    .flatMap(cl -> cl.lookup().stream())
                    .collect(toList());
        }

        return converters;
    }

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
