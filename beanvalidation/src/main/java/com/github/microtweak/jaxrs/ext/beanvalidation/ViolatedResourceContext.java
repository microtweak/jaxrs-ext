package com.github.microtweak.jaxrs.ext.beanvalidation;

import com.github.microtweak.jaxrs.ext.beanvalidation.entity.RestViolation;
import org.apache.commons.lang3.reflect.MethodUtils;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.ArrayUtils.toArray;
import static org.apache.commons.lang3.ClassUtils.getAllInterfaces;
import static org.apache.commons.lang3.ClassUtils.getAllSuperclasses;
import static org.apache.commons.lang3.StringUtils.*;

public class ViolatedResourceContext {

    private Map<String, String> headerParams = new HashMap<>();

    private Map<String, String> pathParams = new HashMap<>();

    private Map<String, String> matrixParam = new HashMap<>();

    private Map<String, String> queryParams = new HashMap<>();

    private Map<String, String> formParams = new HashMap<>();

    private String body;

    public ViolatedResourceContext(ResourceInfo info) {
        final String resourceMethodName = info.getResourceMethod().getName();
        processAnnotatedElements(info.getResourceMethod().getParameters(), resourceMethodName);
    }

    public String getNormalizedPropertyPath(String propertyPath) {
        for (Map<String, String> param : toArray(headerParams, pathParams, matrixParam, queryParams, formParams)) {
            String externalName = param.get(propertyPath);

            if (externalName != null) {
                return externalName;
            }
        }

        if (startsWith(propertyPath, body)) {
            return substringAfter(propertyPath, body + '.');
        }

        return propertyPath;
    }

    public RestViolation.Type getViolationType(String propertyPath) {
        if (headerParams.containsKey(propertyPath)) {
            return RestViolation.Type.HEADER;
        }

        if (pathParams.containsKey(propertyPath)) {
            return RestViolation.Type.PATH;
        }

        if (matrixParam.containsKey(propertyPath)) {
            return RestViolation.Type.MATRIX;
        }

        if (queryParams.containsKey(propertyPath)) {
            return RestViolation.Type.QUERY;
        }

        if (formParams.containsKey(propertyPath)) {
            return RestViolation.Type.FORM;
        }

        return RestViolation.Type.BODY;
    }

    private void processAnnotatedElements(AnnotatedElement[] elements, String parentProperty) {
        if (isNotEmpty(parentProperty)) {
            parentProperty = parentProperty + '.';
        }

        for (AnnotatedElement element : elements) {
            final String propertyPath = defaultIfBlank(parentProperty, "") + getAnnotatedElementName(element);
            processAnnotatedElement(element, propertyPath);
        }
    }

    private void processAnnotatedElement(AnnotatedElement element, String propertyPath) {
        HeaderParam header = getAnnotation(element, HeaderParam.class);
        if (header != null) {
            headerParams.put(propertyPath, header.value());
            return;
        }

        PathParam path = getAnnotation(element, PathParam.class);
        if (path != null) {
            pathParams.put(propertyPath, path.value());
            return;
        }

        MatrixParam matrix = getAnnotation(element, MatrixParam.class);
        if (matrix != null) {
            matrixParam.put(propertyPath, matrix.value());
            return;
        }

        QueryParam query = getAnnotation(element, QueryParam.class);
        if (query != null) {
            queryParams.put(propertyPath, query.value());
            return;
        }

        FormParam form = getAnnotation(element, FormParam.class);
        if (form != null) {
            formParams.put(propertyPath, form.value());
            return;
        }

        if (isAnnotationPresent(element, Valid.class)) {
            body = propertyPath;
        }
    }

    private String getAnnotatedElementName(AnnotatedElement element) {
        if (element instanceof Field) {
            return ((Field) element).getName();
        }

        if (element instanceof Method) {
            return ((Method) element).getName();
        }

        if (element instanceof Parameter) {
            return ((Parameter) element).getName();
        }

        throw new UnsupportedOperationException();
    }

    private Class<?> getBeanParamType(AnnotatedElement element) {
        if (element instanceof Field) {
            return ((Field) element).getType();
        }

        if (element instanceof Method) {
            return ((Method) element).getReturnType();
        }

        if (element instanceof Parameter) {
            return ((Parameter) element).getType();
        }

        throw new UnsupportedOperationException();
    }

    private boolean isAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> annotationType) {
        return getAnnotation(element, annotationType) != null;
    }

    private <A extends Annotation> A getAnnotation(AnnotatedElement element, Class<A> annotationType) {
        final A annotation = element.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }

        if (element instanceof Method) {
            return MethodUtils.getAnnotation((Method) element, annotationType, true, false);
        }

        if (element instanceof Parameter) {
            final Parameter parameterElement = (Parameter) element;
            return getParameterAnnotation(parameterElement.getName(), parameterElement.getDeclaringExecutable(), annotationType);
        }

        return null;
    }

    private <A extends Annotation> A getParameterAnnotation(String parameterName, Executable executable, Class<A> annotationType) {
        final List<Class<?>> allSuperclassAndInterfaces = new ArrayList<>();
        allSuperclassAndInterfaces.addAll( getAllSuperclasses( executable.getDeclaringClass() ) );
        allSuperclassAndInterfaces.addAll( getAllInterfaces( executable.getDeclaringClass() ) );

        for (final Class<?> superType : allSuperclassAndInterfaces) {
            Executable equivalentExecutable;

            try {
                if (executable instanceof Constructor) {
                    equivalentExecutable = superType.getConstructor(executable.getParameterTypes());
                } else {
                    equivalentExecutable = superType.getDeclaredMethod(executable.getName(), executable.getParameterTypes());
                }
            } catch (NoSuchMethodException | SecurityException e) {
                continue;
            }

            for (Parameter parameter : equivalentExecutable.getParameters()) {
                if (!parameter.getName().equals( parameterName )) {
                    continue;
                }

                final A annotation = parameter.getAnnotation(annotationType);
                if (annotation != null) {
                    return annotation;
                }
            }
        }

        return null;
    }

}
