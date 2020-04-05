package com.github.microtweak.jaxrs.ext.multipart.internal;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.util.Optional;

abstract class CommonsUploadMultipartBodyReader<T> extends AbstractMultipartConfigurableBodyReader<T> {

    private static final FileCleaningTracker CLEANING_TRACKER = new FileCleaningTracker();

    protected ServletFileUpload getServletFileUpload() {
        final MultipartConfigElement config = getResourceMultipartConfigElement();

        final DiskFileItemFactory factory = new DiskFileItemFactory();
        final ServletFileUpload upload = new ServletFileUpload(factory);

        factory.setFileCleaningTracker( CLEANING_TRACKER );

        fillConfiguration(config, factory, upload);

        return upload;
    }

    private void fillConfiguration(MultipartConfigElement config, DiskFileItemFactory factory, ServletFileUpload upload) {
        Optional.ofNullable(config.getLocation())
                .filter(StringUtils::isNotBlank)
                .map(File::new)
                .ifPresent(factory::setRepository);

        Optional.ofNullable(config.getFileSizeThreshold()).ifPresent(factory::setSizeThreshold);

        Optional.ofNullable(config.getMaxFileSize()).ifPresent(upload::setFileSizeMax);

        Optional.ofNullable(config.getMaxRequestSize()).ifPresent(upload::setSizeMax);
    }

}
