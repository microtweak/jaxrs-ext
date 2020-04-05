package com.github.microtweak.jaxrs.ext.multipart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Alternative annotation to {@link javax.servlet.annotation.MultipartConfig} allowing configuration in the Resource method or class.
 *
 * <br><br>
 *
 * The annotation {@link javax.servlet.annotation.MultipartConfig} can also be accepted. However, this annotation must be
 * placed only in the Resource class or in the class that inherits from {@link javax.ws.rs.core.Application}
 *
 * <br><br>
 *
 * The order to read the Multipart configuration is:
 * <ol>
 * <li>Resource Method</li>
 * <li>Resource Class</li>
 * <li>Class inheriting from Application</li>
 * </ol>
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceMultipartConfig {

    /**
     * The directory location where files will be stored
     *
     * @return the directory location where files will be stored
     */
    String location() default "";

    /**
     * The maximum size allowed for uploaded files.
     *
     * <p>The default is <code>-1L</code>, which means unlimited.
     *
     * @return the maximum size allowed for uploaded files
     */
    long maxFileSize() default -1L;

    /**
     * The maximum size allowed for <code>multipart/form-data</code>
     * requests
     *
     * <p>The default is <code>-1L</code>, which means unlimited.
     *
     * @return the maximum size allowed for <code>multipart/form-data</code> requests
     */
    long maxRequestSize() default -1L;

    /**
     * The size threshold after which the file will be written to disk
     *
     * @return the size threshold after which the file will be written to disk
     */
    int fileSizeThreshold() default 0;

}
