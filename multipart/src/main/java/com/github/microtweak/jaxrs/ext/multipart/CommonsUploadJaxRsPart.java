package com.github.microtweak.jaxrs.ext.multipart;

import lombok.AllArgsConstructor;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class CommonsUploadJaxRsPart implements Part {

    private FileItem fileItem;

    @Override
    public InputStream getInputStream() throws IOException {
        return fileItem.getInputStream();
    }

    @Override
    public String getContentType() {
        return fileItem.getContentType();
    }

    @Override
    public String getName() {
        return fileItem.getFieldName();
    }

    @Override
    public String getSubmittedFileName() {
        return fileItem.getName();
    }

    @Override
    public long getSize() {
        return fileItem.getSize();
    }

    @Override
    public void write(String fileName) throws IOException {
        try {
            fileItem.write(new File(fileName));
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public void delete() throws IOException {
        fileItem.delete();
    }

    @Override
    public String getHeader(String name) {
        return fileItem.getHeaders().getHeader(name);
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return fromIteratorToCollection(fileItem.getHeaders().getHeaders(name));
    }

    @Override
    public Collection<String> getHeaderNames() {
        return fromIteratorToCollection(fileItem.getHeaders().getHeaderNames());
    }

    private <T> Collection<T> fromIteratorToCollection(Iterator<T> iterator) {
        final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
        return StreamSupport.stream(spliterator, false).collect(Collectors.toList());
    }

}
