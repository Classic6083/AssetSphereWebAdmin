package com.assetsphere.config;

public final class DemoConfig {
    private DemoConfig() {
    }

    public static boolean isEnabled() {
        return Boolean.parseBoolean(System.getProperty("demoMode", TestConfig.get("demoMode")));
    }

    public static int stepPauseSeconds() {
        return Integer.parseInt(System.getProperty("stepPauseSeconds", TestConfig.get("stepPauseSeconds")));
    }

    public static int holdBrowserOpenSeconds() {
        return Integer.parseInt(System.getProperty("holdBrowserOpenSeconds", TestConfig.get("holdBrowserOpenSeconds")));
    }
}
