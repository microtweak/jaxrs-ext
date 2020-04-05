package com.github.microtweak.jaxrs.ext.multipart.internal;

import com.github.microtweak.jaxrs.ext.multipart.ResourceMultipartConfig;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.reflect.TypeUtils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Providers;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

abstract class AbstractMultipartConfigurableBodyReader<T> implements MessageBodyReader<T> {

    @Context
    private Application application;

    @Context
    private Providers providers;

    @Getter(AccessLevel.PROTECTED)
    @Context
    private ResourceInfo resourceInfo;

    @Getter(AccessLevel.PROTECTED)
    @Context
    private HttpServletRequest request;

    private Map<ResourceInfo, MultipartConfigElement> resourceMultipartConfigElement = new ConcurrentHashMap<>();

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        TypeVariable<?> typeVar = MessageBodyReader.class.getTypeParameters()[0];
        return TypeUtils.getRawType(typeVar, getClass()).isAssignableFrom( type );
    }

    protected MultipartConfigElement getResourceMultipartConfigElement() {
        return resourceMultipartConfigElement.computeIfAbsent(resourceInfo, (k) -> this.getMultipartConfigElementFromResourceOrGlobal());
    }

    private MultipartConfigElement getMultipartConfigElementFromResourceOrGlobal() {
        Optional<MultipartConfigElement> config = Optional.ofNullable( resourceInfo.getResourceMethod().getAnnotation(ResourceMultipartConfig.class) )
                .map(this::ofResourceMultipartConfig);

        if (!config.isPresent()) {
            config = Optional.ofNullable( resourceInfo.getResourceClass().getAnnotation(ResourceMultipartConfig.class) )
                    .map(this::ofResourceMultipartConfig);
        }

        if (!config.isPresent()) {
            config = Optional.ofNullable( resourceInfo.getResourceClass().getAnnotation(MultipartConfig.class) )
                    .map(MultipartConfigElement::new);
        }

        if (!config.isPresent()) {
            config = Optional.ofNullable(application)
                    .map(obj -> obj.getClass().getAnnotation(MultipartConfig.class))
                    .map(MultipartConfigElement::new);
        }

        if (!config.isPresent()) {
            config = Optional.ofNullable( providers.getContextResolver(MultipartConfigElement.class, MediaType.MULTIPART_FORM_DATA_TYPE) )
                    .map(ctx -> ctx.getContext(null));
        }

        return config.orElse(null); // TODO throw exception if MultipartConfigElement is not present
    }

    private MultipartConfigElement ofResourceMultipartConfig(ResourceMultipartConfig cfg) {
        return new MultipartConfigElement(cfg.location(), cfg.maxFileSize(), cfg.maxRequestSize(), cfg.fileSizeThreshold());
    }

}
