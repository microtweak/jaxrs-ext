package com.github.microtweak.jaxrs.ext.param.impl;

import com.github.microtweak.jaxrs.ext.param.ConverterLookup;
import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceLoaderConverterLookup implements ConverterLookup {

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public List<RestParamConverter<?>> lookup() {
        List<RestParamConverter<?>> converters = new ArrayList<>();

        ServiceLoader.load(RestParamConverter.class).forEach(converters::add);

        return converters;
    }

}
