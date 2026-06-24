package com.assetsphere.support.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class StagingDataConfig {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = StagingDataConfig.class.getClassLoader().getResourceAsStream("staging/staging-config.properties")) {
            if (input == null) {
                throw new IllegalStateException("staging/staging-config.properties was not found in src/test/resources");
            }
            PROPERTIES.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not load staging configuration", exception);
        }
    }

    private StagingDataConfig() {
    }

    public static String get(String key) {
        return System.getProperty(key, PROPERTIES.getProperty(key, ""));
    }

    public static boolean isEnabled() {
        return Boolean.parseBoolean(get("staging.enabled"));
    }

    public static String mode() {
        return get("staging.mode");
    }

    public static boolean isMockMode() {
        return "mock".equalsIgnoreCase(mode()) || mode().isBlank();
    }

    public static String baseUrl() {
        return get("staging.baseUrl");
    }

    public static String seedEndpoint() {
        return get("staging.seedEndpoint");
    }

    public static String cleanupEndpoint() {
        return get("staging.cleanupEndpoint");
    }

    public static int requestTimeoutSeconds() {
        return Integer.parseInt(get("staging.requestTimeoutSeconds"));
    }
}
