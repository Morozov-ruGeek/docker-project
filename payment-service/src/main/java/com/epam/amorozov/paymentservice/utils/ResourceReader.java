package com.epam.amorozov.paymentservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class ResourceReader {

    private final ResourceLoader resourceLoader;

    @Autowired
    public ResourceReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String readFileToString(String path) {
        Resource resource = resourceLoader.getResource(path);
        return asString(resource);
    }

    private String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
