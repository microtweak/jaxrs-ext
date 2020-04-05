package com.github.microtweak.jaxrs.ext.multipart.internal;

import com.github.microtweak.jaxrs.ext.multipart.CommonsUploadJaxRsPart;
import com.github.microtweak.jaxrs.ext.multipart.Multipart;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.stream.Stream;

@Provider
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ServletMultipartBodyReader extends CommonsUploadMultipartBodyReader<Part> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        boolean isAnnotatedWithMultipart = Stream.of(annotations).anyMatch(a -> a.annotationType().equals(Multipart.class));
        return isAnnotatedWithMultipart && super.isReadable(type, genericType, annotations, mediaType);
    }

    @Override
    public Part readFrom(Class<Part> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        final String fieldName = Stream.of(annotations)
                .filter(a -> a.annotationType().equals(Multipart.class))
                .map(a -> ((Multipart) a).value())
                .filter(StringUtils::isNotBlank)
                .findFirst()
                .orElse(null);

        try {
            return getServletFileUpload().parseRequest( getRequest() ).stream()
                    .filter(v -> v.getFieldName().equals(fieldName))
                    .map(CommonsUploadJaxRsPart::new)
                    .findFirst()
                    .orElse(null);
        } catch (FileUploadException e) {
            throw new WebApplicationException(e);
        }
    }

}
