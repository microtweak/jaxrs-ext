package com.github.microtweak.jaxrs.ext.param;

public class ParamConvertException extends RuntimeException {

    public ParamConvertException(String message) {
        super(message);
    }

    public ParamConvertException(String message, Throwable cause) {
        super(message, cause);
    }

}
