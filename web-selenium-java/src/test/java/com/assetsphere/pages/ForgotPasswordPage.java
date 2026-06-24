package com.assetsphere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {
    public ForgotPasswordPage(WebDriver driver, int timeoutSeconds) {
        super(driver, timeoutSeconds);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl.endsWith("/") ? baseUrl + "forgot-password" : baseUrl + "/forgot-password");
    }

    public boolean isLoaded() {
        String text = bodyText().toLowerCase();
        return driver.getCurrentUrl().toLowerCase().contains("forgot")
                || text.contains("forgot")
                || text.contains("reset")
                || isAnyVisible(
                        By.cssSelector("input[type='email']"),
                        By.cssSelector("input[name='email']"),
                        By.cssSelector("button[type='submit']")
                );
    }
}
