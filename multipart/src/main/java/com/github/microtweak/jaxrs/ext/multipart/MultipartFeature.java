package com.github.microtweak.jaxrs.ext.multipart;

import com.github.microtweak.jaxrs.ext.multipart.internal.MultipartBodyReader;
import com.github.microtweak.jaxrs.ext.multipart.internal.ServletMultipartBodyReader;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class MultipartFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(ServletMultipartBodyReader.class);
        context.register(MultipartBodyReader.class);

        return true;
    }

}
