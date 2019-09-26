package org.apache.pricecontroller.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Component
class PriceFileProvider {

    private final Path filePath;

    PriceFileProvider(@Value("${price.file.path}") String filePath) {
        this.filePath = Paths.get(filePath);
    }

    String getLastPrice() {
        try {


            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            List<String> lines = Files.readAllLines(filePath);

            return lines.size() > 0 ? lines.get(lines.size() - 1) : null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void appendPrice(String price) {
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            Files.write(filePath, price.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
