package com.icl.html_engine.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Objects;

@Data
@ConfigurationProperties(prefix = "html-file")
@Component
public class FileHandler {
    private String inputName;
    private String outputName;

    public File writeToFile(String textToFile) throws IOException {
        File outputFile = new File(
                Objects.requireNonNull(Thread.currentThread()
                        .getContextClassLoader()
                        .getResource("."))
                        .getPath()
                        + outputName);

        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            fileWriter.write(textToFile);
        }

        return outputFile;
    }

    public String readFileToString() throws IOException {
        File inputFile = new File(Objects.requireNonNull(Thread
                .currentThread()
                .getContextClassLoader()
                .getResource("."))
                .getPath()
                + inputName);

        StringBuilder stringFromFile;
        try (Reader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            stringFromFile = new StringBuilder();
            while (bufferedReader.ready()) {
                stringFromFile.append(bufferedReader.readLine());
            }
        }

        return stringFromFile.toString();
    }
}
