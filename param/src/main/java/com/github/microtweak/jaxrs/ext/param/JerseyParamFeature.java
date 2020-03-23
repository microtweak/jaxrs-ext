package com.github.microtweak.jaxrs.ext.param;

import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
@ConstrainedTo(RuntimeType.SERVER)
public class JerseyParamFeature implements ForcedAutoDiscoverable {

    @Override
    public void configure(FeatureContext context) {
        context.register(RestParamConverterProvider.class);
    }

}
