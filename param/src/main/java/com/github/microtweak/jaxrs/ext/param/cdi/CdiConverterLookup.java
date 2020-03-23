package com.github.microtweak.jaxrs.ext.param.cdi;

import com.github.microtweak.jaxrs.ext.param.spi.ConverterLookup;
import com.github.microtweak.jaxrs.ext.param.RestParamConverter;
import lombok.extern.java.Log;

import javax.enterprise.inject.spi.CDI;
import java.util.List;
import java.util.logging.Level;

import static java.util.stream.Collectors.toList;

@Log
public class CdiConverterLookup implements ConverterLookup {

    @Override
    public boolean isAvailable() {
        try {
            CDI.current();
            return true;
        } catch (Throwable e) {
            log.log(Level.FINEST, "CDI is not available!", e);
            return false;
        }
    }

    @Override
    public List<RestParamConverter<?>> lookup() {
        return CDI.current()
                .select(new RestParamConverterTypeLiteral())
                .stream()
                .collect(toList());
    }

}
