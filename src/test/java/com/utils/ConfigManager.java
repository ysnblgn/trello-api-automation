package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Properties PROPERTIES = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static {
        try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                logger.error("{} file not found in the classpath.", CONFIG_FILE);
                throw new RuntimeException(CONFIG_FILE + " not found.");
            }
            PROPERTIES.load(inputStream);
            logger.info("{} loaded successfully.", CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Error loading {}: {}", CONFIG_FILE, e.getMessage());
            throw new RuntimeException("Could not load " + CONFIG_FILE, e);
        }
    }

    public static String getProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in {}.", key, CONFIG_FILE);
        }
        return value;
    }
}
