package com.github.microtweak.jaxrs.ext.multipart;

import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
@ConstrainedTo(RuntimeType.SERVER)
public class JerseyMultipartFeature implements ForcedAutoDiscoverable {

    @Override
    public void configure(FeatureContext context) {
        context.register(MultipartFeature.class);
    }

}
