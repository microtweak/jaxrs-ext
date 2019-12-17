package com.github.microtweak.jaxrs.ext.beanvalidation;

import com.github.microtweak.jaxrs.ext.beanvalidation.entity.RestViolation;
import com.github.microtweak.jaxrs.ext.beanvalidation.entity.RestViolations;
import com.github.microtweak.jaxrs.ext.beanvalidation.entity.UnexpectedBeanValidationMessage;
import lombok.extern.java.Log;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Log
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public Response toResponse(ValidationException exception) {
        Response.ResponseBuilder resp = null;

        if (exception instanceof ConstraintViolationException) {
            final ViolatedResourceContext ctx = new ViolatedResourceContext( resourceInfo );

            final List<RestViolation> violations = ConstraintViolationException.class.cast( exception )
                    .getConstraintViolations().stream()
                    .map(v -> toRestViolation(v, ctx))
                    .collect(Collectors.toList());

            resp = Response.status(Response.Status.PRECONDITION_FAILED)
                    .entity( new RestViolations(violations) );
        }
        else {
            log.log(Level.WARNING, "A ValidationException was thrown", exception);
            resp = Response.serverError().entity( new UnexpectedBeanValidationMessage(exception.getMessage()) );
        }

        return resp.build();
    }

    private RestViolation toRestViolation(ConstraintViolation<?> violation, ViolatedResourceContext ctx) {
        RestViolation.Type type = null;
        String path = violation.getPropertyPath().toString();

        if (resourceInfo.getResourceClass().equals(violation.getRootBeanClass())) {
            type = ctx.getViolationType(path);
            path = ctx.getNormalizedPropertyPath(path);
        }

        final String msg = capitalize(violation.getMessage());
        return new RestViolation(path, type, msg);
    }

}
