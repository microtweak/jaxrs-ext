package com.github.microtweak.jaxrs.ext.beanvalidation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RestViolations {

    private List<RestViolation> violations;

}
