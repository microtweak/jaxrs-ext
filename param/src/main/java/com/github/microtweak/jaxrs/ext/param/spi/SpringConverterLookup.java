package com.github.microtweak.jaxrs.ext.param.spi;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.util.List;

public class SpringConverterLookup implements ConverterLookup {

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public List<RestParamConverter<?>> lookup() {
        return null;
    }

}
