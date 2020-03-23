package com.github.microtweak.jaxrs.ext.param.spi;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.util.List;

public interface ConverterLookup {

    boolean isAvailable();

    List<RestParamConverter<?>> lookup();

}
