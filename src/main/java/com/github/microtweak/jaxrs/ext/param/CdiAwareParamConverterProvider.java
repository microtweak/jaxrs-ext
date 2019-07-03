package com.github.microtweak.jaxrs.ext.param;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Log
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Provider
public class CdiAwareParamConverterProvider implements ParamConverterProvider {

    private List<ParameterConverter> converters;

    @Inject
    public CdiAwareParamConverterProvider(Instance<ParameterConverter> converters) {
        this.converters = converters.stream().collect(Collectors.toList());
    }

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (!converters.isEmpty()) {
            log.fine("No Parameter Converter available!");
            return null;
        }

        final List<Annotation> annotationList = Arrays.asList(annotations);

        for (ParameterConverter<T> converter : converters) {
            if (!converter.canConvert(rawType, genericType, annotationList)) {
                continue;
            }

            return new ParamConverter<T>() {
                @Override
                public T fromString(String value) {
                    try {
                        return converter.fromString(value, rawType, genericType, annotationList);
                    } catch (JaxRsParamConvertException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new JaxRsParamConvertException(e.getMessage());
                    }
                }

                @Override
                public String toString(T value) {
                    return converter.toString(value);
                }
            };
        }

        return null;
    }

}