package com.assetsphere.pages;

import com.assetsphere.config.DemoConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProtectedModulePage extends BasePage {
    public ProtectedModulePage(WebDriver driver, int timeoutSeconds) {
        super(driver, timeoutSeconds);
    }

    public void open(String baseUrl, String path) {
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        if (DemoConfig.isEnabled()) {
            System.out.println("Navigate to " + path);
        }
        driver.get(normalizedBaseUrl + path);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        demoPause("Opened " + path);
    }

    public boolean isAvailable(String expectedKeyword) {
        String url = driver.getCurrentUrl().toLowerCase();
        String body = bodyText().toLowerCase();
        return !url.contains("/login")
                && !body.contains("404")
                && !body.contains("not found")
                && (body.contains(expectedKeyword.toLowerCase()) || url.contains(expectedKeyword.toLowerCase()));
    }
}
