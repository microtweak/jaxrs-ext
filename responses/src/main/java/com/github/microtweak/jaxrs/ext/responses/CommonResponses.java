package com.github.microtweak.jaxrs.ext.responses;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

public final class CommonResponses {

    private CommonResponses() {
        throw new RuntimeException("This class is for static access only");
    }

    public static Response status(Status status, Object entity) {
        ResponseBuilder rb = Response.status(status);

        if (entity != null) {
            rb.entity(entity);
        }

        return rb.build();
    }

    public static Response status(Status status) {
        return status(status, null);
    }

    public static Response ok(Object entity) {
        return status(Status.OK, entity);
    }

    public static Response ok() {
        return status(Status.OK);
    }

    public static Response noContent() {
        return status(Status.NO_CONTENT);
    }

    public static Response noContent(Object entity) {
        return status(Status.NO_CONTENT, entity);
    }

    public static Response badRequest() {
        return status(Status.BAD_REQUEST);
    }

    public static Response badRequest(Object entity) {
        return status(Status.BAD_REQUEST, entity);
    }

    public static Response unauthorized() {
        return status(Status.UNAUTHORIZED);
    }

    public static Response unauthorized(Object entity) {
        return status(Status.UNAUTHORIZED, entity);
    }

    public static Response forbidden() {
        return status(Status.FORBIDDEN);
    }

    public static Response forbidden(Object entity) {
        return status(Status.FORBIDDEN, entity);
    }

    public static Response notFound() {
        return status(Status.NOT_FOUND);
    }

    public static Response notFound(Object entity) {
        return status(Status.NOT_FOUND, entity);
    }

    public static Response serverError() {
        return Response.serverError().build();
    }

    public static Response serverError(Object entity) {
        return Response.serverError().entity(entity).build();
    }

}
