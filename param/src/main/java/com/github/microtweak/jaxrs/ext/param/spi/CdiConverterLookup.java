package com.github.microtweak.jaxrs.ext.param.spi;

import com.github.microtweak.jaxrs.ext.param.RestParamConverter;
import lombok.extern.java.Log;

import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;
import java.util.List;
import java.util.logging.Level;

import static java.util.stream.Collectors.toList;

@Log
public class CdiConverterLookup implements ConverterLookup {

    private CDI<Object> cdi;

    @Override
    public boolean isAvailable() {
        try {
            cdi = CDI.current();
            return true;
        } catch (Exception e) {
            log.log(Level.FINEST, "CDI is not available!", e);
            return false;
        }
    }

    @Override
    public List<RestParamConverter<?>> lookup() {
        return cdi.select(new RestParamConverterTypeLiteral())
                .stream()
                .collect(toList());
    }

    private class RestParamConverterTypeLiteral extends TypeLiteral<RestParamConverter<?>> {
    }

}
