package com.assetsphere.pages;

import java.time.Duration;
import java.util.Arrays;

import com.assetsphere.config.DemoConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    protected WebElement firstVisible(By... locators) {
        return Arrays.stream(locators)
                .map(locator -> {
                    try {
                        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    } catch (RuntimeException ignored) {
                        return null;
                    }
                })
                .filter(element -> element != null)
                .findFirst()
                .orElseThrow(() -> new AssertionError("None of the expected elements were visible"));
    }

    protected void clickFirst(By... locators) {
        clickFirst("Click element", locators);
    }

    protected void clickFirst(String label, By... locators) {
        WebElement element = firstVisible(locators);
        demoInteract(label, element, element::click);
    }

    protected void typeFirst(String value, By... locators) {
        typeFirst("Type text", value, locators);
    }

    protected void typeFirst(String label, String value, By... locators) {
        WebElement element = firstVisible(locators);
        demoInteract(label, element, () -> {
            element.clear();
            element.sendKeys(value);
        });
    }

    protected boolean isAnyVisible(By... locators) {
        return Arrays.stream(locators).anyMatch(locator -> {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
            } catch (RuntimeException ignored) {
                return false;
            }
        });
    }

    protected String bodyText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))).getText();
    }

    protected String localStorageValue(String key) {
        return (String) ((JavascriptExecutor) driver).executeScript("return window.localStorage.getItem(arguments[0]);", key);
    }

    protected void demoPause(String label) {
        if (!DemoConfig.isEnabled()) {
            return;
        }
        String message = label == null || label.isBlank() ? "Paused for demo viewing" : label;
        System.out.println(message);
        try {
            Thread.sleep(DemoConfig.stepPauseSeconds() * 1000L);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    protected void demoInteract(String label, WebElement element, Runnable action) {
        if (DemoConfig.isEnabled()) {
            highlight(element);
            System.out.println(label);
        }
        action.run();
        demoPause(label);
    }

    protected void highlight(WebElement element) {
        if (!(driver instanceof JavascriptExecutor javascriptExecutor)) {
            return;
        }

        try {
            javascriptExecutor.executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'center'});" +
                            "arguments[0].style.transition='all 0.2s ease';" +
                            "arguments[0].style.outline='3px solid #ffbd59';" +
                            "arguments[0].style.boxShadow='0 0 0 4px rgba(255,189,89,0.35)';",
                    element
            );
        } catch (RuntimeException ignored) {
            // Best effort only for visual runs.
        }
    }
}
