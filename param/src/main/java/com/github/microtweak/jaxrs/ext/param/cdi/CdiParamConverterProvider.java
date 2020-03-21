package com.github.microtweak.jaxrs.ext.param.cdi;

import com.github.microtweak.jaxrs.ext.param.AbstractParamConverterProvider;
import com.github.microtweak.jaxrs.ext.param.RestParamConverter;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CdiParamConverterProvider extends AbstractParamConverterProvider {

    @Inject
    private Instance<RestParamConverter> converters;

    @Override
    protected List<RestParamConverter> getConverters() {
        return converters.stream().collect(toList());
    }

}
