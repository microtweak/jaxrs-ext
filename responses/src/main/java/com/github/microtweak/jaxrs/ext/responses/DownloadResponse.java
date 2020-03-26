package com.github.microtweak.jaxrs.ext.responses;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static javax.ws.rs.core.HttpHeaders.CONTENT_DISPOSITION;
import static javax.ws.rs.core.HttpHeaders.CONTENT_LENGTH;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DownloadResponse {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    @NonNull
    private StreamingOutput stream;
    private long size;

    private String contentType = APPLICATION_OCTET_STREAM;

    private boolean forceDownload;
    private String fileName;

    public static DownloadResponse ofFile(File file) {
        try {
            return ofInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    public static DownloadResponse ofPath(Path path) {
        try {
            return ofInputStream(Files.newInputStream(path));
        } catch (IOException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    public static DownloadResponse ofBytes(byte[] bytes) {
        return ofInputStream(new ByteArrayInputStream(bytes));
    }

    public static DownloadResponse ofInputStream(InputStream input) {
        Objects.requireNonNull(input, "input");

        final StreamingOutput stream = output -> {
            try {
                int read;
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

                while ((read = input.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0) {
                    output.write(buffer, 0, read);
                }
            } finally {
                input.close();
            }
        };

        return new DownloadResponse( stream );
    }

    public DownloadResponse size(long size) {
        this.size = size;
        return this;
    }

    public DownloadResponse contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public DownloadResponse contentType(MediaType contentType) {
        this.contentType = contentType.toString();
        return this;
    }

    public DownloadResponse forceDownload(boolean forceDownload) {
        this.forceDownload = forceDownload;
        return this;
    }

    public DownloadResponse fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Response build() {
        String contentDisposition = forceDownload ? "attachment" : "inline";

        if (isNotBlank(fileName)) {
            contentDisposition += String.format("; filename=\"%s\"", fileName);
        }

        final Response.ResponseBuilder resp = Response.ok(stream, contentType)
                .header(CONTENT_DISPOSITION, contentDisposition);

        if (size > 0) {
            resp.header(CONTENT_LENGTH, Long.toString(size));
        }

        return resp.build();
    }

}
