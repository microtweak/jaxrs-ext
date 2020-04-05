package com.github.microtweak.jaxrs.ext.multipart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.Part;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class MultipartBody {

    @Getter
    private MediaType mediaType;
    private Map<String, List<FileItem>> parameterMap;

    private Optional<List<FileItem>> getParameterAsOptional(String fieldName) {
        return Optional.ofNullable( parameterMap.get(fieldName) );
    }

    public List<Part> getParts(String fieldName) {
        return getParameterAsOptional(fieldName)
                .map(list -> list.stream()
                        .map(CommonsUploadJaxRsPart::new)
                        .map(part -> (Part) part)
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
    }

    public Part getPart(String fieldName) {
        return getParameterAsOptional(fieldName)
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .map(CommonsUploadJaxRsPart::new)
                .orElse(null);
    }

    public List<String> getParameters(String fieldName) {
        return getParameterAsOptional(fieldName)
                .map(list -> list.stream()
                        .map(item -> new String(item.get(), StandardCharsets.UTF_8))
                        .collect(toList())
                )
                .orElse(Collections.emptyList());
    }

    public String getParameter(String fieldName) {
        return getParameterAsOptional(fieldName)
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .map(item -> new String(item.get(), StandardCharsets.UTF_8))
                .orElse(null);
    }

    public Collection<Part> getAllParts() {
        return parameterMap.values().stream()
                .flatMap(p -> p.stream().map(CommonsUploadJaxRsPart::new))
                .collect(toList());
    }
}
