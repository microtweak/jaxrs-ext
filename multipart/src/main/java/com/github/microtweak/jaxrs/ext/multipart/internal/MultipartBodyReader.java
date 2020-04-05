package com.github.microtweak.jaxrs.ext.multipart.internal;

import com.github.microtweak.jaxrs.ext.multipart.MultipartBody;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Provider
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class MultipartBodyReader extends CommonsUploadMultipartBodyReader<MultipartBody> {

    @Override
    public MultipartBody readFrom(Class<MultipartBody> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        try {
            final Map<String, List<FileItem>> parameterMap = getServletFileUpload().parseParameterMap(getRequest());
            return new MultipartBody(mediaType, parameterMap);
        } catch (FileUploadException e) {
            throw new WebApplicationException(e);
        }
    }

}
