package com.github.microtweak.jaxrs.ext.param;

import org.apache.commons.lang3.reflect.TypeUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Objects;

public interface RestParamConverter<T> {

    default boolean canConvert(Class<T> rawType, Type genericType, List<Annotation> annotations) {
        TypeVariable<?> typeVar = RestParamConverter.class.getTypeParameters()[0];
        return TypeUtils.getRawType(typeVar, getClass()).isAssignableFrom( rawType );
    }

    T fromString(String value, Class<T> rawType, Type genericType, List<Annotation> annotations);

    default String toString(T value) {
        return Objects.toString(value, null);
    }

}
