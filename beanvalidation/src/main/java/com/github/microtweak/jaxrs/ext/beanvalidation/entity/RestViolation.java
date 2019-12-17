package com.github.microtweak.jaxrs.ext.beanvalidation.entity;

import lombok.Getter;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
public class RestViolation {

    public enum Type {
        HEADER, PATH, MATRIX, QUERY, FORM, BODY
    }

    private String name;
    private Type type;
    private String message;

    public RestViolation(String name, Type type, String message) {
        this.name = name;
        this.type = defaultIfNull(type, Type.BODY);
        this.message = message;
    }
}
