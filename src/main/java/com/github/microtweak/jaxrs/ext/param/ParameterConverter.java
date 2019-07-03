package com.github.microtweak.jaxrs.ext.param;

import javax.enterprise.inject.Vetoed;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

@Vetoed
public interface ParameterConverter<T> {

    boolean canConvert(Class<T> rawType, Type genericType, List<Annotation> annotations);

    T fromString(String value, Class<T> rawType, Type genericType, List<Annotation> annotations);

    default String toString(T value) {
        return Objects.toString(value, null);
    }

}
