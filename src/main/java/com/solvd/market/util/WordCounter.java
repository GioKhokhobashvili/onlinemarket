package com.solvd.market.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class WordCounter {

    private static final Logger LOGGER = LogManager.getLogger(WordCounter.class);

    public static void countAndSave(String inputPath, String outputPath) {
        try {
            String content = FileUtils.readFileToString(new File(inputPath), StandardCharsets.UTF_8);

            long uniqueWordCount = Arrays.stream(StringUtils.split(content.toLowerCase()))
                    .map(word -> word.replaceAll("[^a-zA-Z]", ""))
                    .filter(StringUtils::isNotBlank)
                    .distinct()
                    .count();

            FileUtils.writeStringToFile(new File(outputPath), "Unique words: " + uniqueWordCount, StandardCharsets.UTF_8);
            LOGGER.info("Unique word count: {}. Result saved to {}", uniqueWordCount, outputPath);
        } catch (Exception e) {
            LOGGER.error("Failed to process file: {}", e.getMessage());
        }
    }
}