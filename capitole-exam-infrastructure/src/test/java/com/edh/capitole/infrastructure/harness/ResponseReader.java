package com.edh.capitole.infrastructure.harness;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class ResponseReader {

    private ResponseReader() {
    }

    public static String readJsonResponse(String fileName) throws IOException {
        File file = ResourceUtils.getFile("classpath:responses/" + fileName + ".json");
        return new String(new FileInputStream(file).readAllBytes());
    }
}
