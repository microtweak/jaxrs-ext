package com.github.microtweak.jaxrs.ext.param.spring;

import com.github.microtweak.jaxrs.ext.param.AbstractParamConverterProvider;
import com.github.microtweak.jaxrs.ext.param.RestParamConverter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SpringParamConverterProvider extends AbstractParamConverterProvider {

    @Getter
    @Autowired(required = false)
    private List<RestParamConverter> converters = new ArrayList<>();

}
