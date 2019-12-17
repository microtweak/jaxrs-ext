package com.github.microtweak.jaxrs.ext.beanvalidation;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
@ConstrainedTo(RuntimeType.SERVER)
public class BvExceptionMapperFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        // RESTEasy seems to ignore @ConstrainedTo in some cases
        if (context.getConfiguration().getRuntimeType() != RuntimeType.SERVER) {
            return false;
        }

        context.register(ValidationExceptionMapper.class);
        return true;
    }

}
