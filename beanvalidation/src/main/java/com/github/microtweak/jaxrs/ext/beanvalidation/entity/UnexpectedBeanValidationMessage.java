package com.github.microtweak.jaxrs.ext.beanvalidation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnexpectedBeanValidationMessage {

    private String message;

}
