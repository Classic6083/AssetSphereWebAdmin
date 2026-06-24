package com.assetsphere.pages;

import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class RequestAddPage extends BasePage {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public RequestAddPage(WebDriver driver, int timeoutSeconds) {
        super(driver, timeoutSeconds);
    }

    public void open(String baseUrl) {
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        driver.get(normalizedBaseUrl + "/requests/add");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        demoPause("Request form loaded");
    }

    public void chooseRequestType(String... labels) {
        for (String label : labels) {
            try {
                clickFirst("Select request type " + label,
                        By.xpath("//*[self::button or self::span or self::div or self::label][contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'" + label.toLowerCase() + "')]"),
                        By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'" + label.toLowerCase() + "')]")
                );
                return;
            } catch (RuntimeException ignored) {
            }
        }
        throw new AssertionError("Unable to select a request type");
    }

    public void chooseCategoryFromFirstOption() {
        WebElement selectElement = Arrays.stream(driver.findElements(By.tagName("select")).toArray(new WebElement[0]))
                .findFirst()
                .orElse(null);
        if (selectElement != null) {
            Select select = new Select(selectElement);
            if (select.getOptions().size() > 1) {
                select.selectByIndex(1);
                demoPause("Category selected");
                return;
            }
        }

        clickFirst("Open category selector",
                By.xpath("//*[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'category')]"),
                By.xpath("//*[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'select category')]")
        );

        clickFirst("Pick first category option",
                By.xpath("(//*[self::li or self::div or self::span][@role='option' or contains(@class,'option') or contains(@class,'item')])[1]"),
                By.xpath("(//option)[2]")
        );
        demoPause("Category selected");
    }

    public void enterJustification(String text) {
        typeFirst("Enter justification", text,
                By.cssSelector("textarea"),
                By.cssSelector("textarea[name*='justif' i]"),
                By.cssSelector("textarea[placeholder*='justif' i]"),
                By.xpath("//textarea | //input[contains(translate(@placeholder,'" + UPPERCASE + "','" + LOWERCASE + "'),'justif')]")
        );
    }

    public void choosePriority(String... labels) {
        for (String label : labels) {
            try {
                clickFirst("Select priority " + label,
                        By.xpath("//*[self::button or self::span or self::div or self::label][contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'" + label.toLowerCase() + "')]"),
                        By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'" + label.toLowerCase() + "')]")
                );
                demoPause("Priority selected");
                return;
            } catch (RuntimeException ignored) {
            }
        }
    }

    public void submit() {
        clickFirst("Submit request",
                By.cssSelector("button[type='submit']"),
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'submit')]"),
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'create request')]")
        );
        demoPause("Request submitted");
    }

    public boolean isLoaded() {
        return bodyText().toLowerCase().contains("request");
    }

    public boolean isSuccess() {
        String text = bodyText().toLowerCase();
        String url = driver.getCurrentUrl().toLowerCase();
        return text.contains("success")
                || text.contains("submitted")
                || text.contains("created")
                || url.contains("/requests")
                || !url.contains("/requests/add");
    }
}
