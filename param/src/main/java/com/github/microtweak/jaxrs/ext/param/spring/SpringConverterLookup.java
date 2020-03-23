package com.github.microtweak.jaxrs.ext.param.spring;

import com.github.microtweak.jaxrs.ext.param.ConverterLookup;
import com.github.microtweak.jaxrs.ext.param.RestParamConverter;
import lombok.extern.java.Log;

import java.util.List;
import java.util.logging.Level;

@Log
public class SpringConverterLookup implements ConverterLookup {

    @Override
    public boolean isAvailable() {
        try {
            return RestParamConverterAutoConfiguration.isAvailable();
        } catch (Throwable e) {
            log.log(Level.FINEST, "Spring DI is not available!", e);
            return false;
        }
    }

    @Override
    public List<RestParamConverter<?>> lookup() {
        return RestParamConverterAutoConfiguration.getRestParamConverter();
    }

}