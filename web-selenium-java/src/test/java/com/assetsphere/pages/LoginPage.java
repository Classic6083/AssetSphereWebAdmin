package com.assetsphere.pages;

import com.assetsphere.config.DemoConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public LoginPage(WebDriver driver, int timeoutSeconds) {
        super(driver, timeoutSeconds);
    }

    public void open(String baseUrl) {
        String url = baseUrl.endsWith("/") ? baseUrl + "login" : baseUrl + "/login";
        if (DemoConfig.isEnabled()) {
            System.out.println("Open login page");
        }
        driver.get(url);
        demoPause("Login page loaded");
    }

    public DashboardPage login(String email, String password) {
        if (DemoConfig.isEnabled()) {
            System.out.println("Start login flow");
        }
        enterEmail(email);
        enterPassword(password);
        submit();

        demoPause("Login submitted; waiting on dashboard");
        return new DashboardPage(driver, wait);
    }

    public void enterEmail(String email) {
        typeFirst("Enter email", email,
                By.cssSelector("input[type='email']"),
                By.cssSelector("input[name='email']"),
                By.cssSelector("input[name='username']"),
                By.cssSelector("input[id*='email' i]"),
                By.cssSelector("input[placeholder*='email' i]"),
                By.xpath("//input[contains(translate(@placeholder,'" + UPPERCASE + "','" + LOWERCASE + "'),'user')]")
        );
    }

    public void enterPassword(String password) {
        typeFirst("Enter password", password,
                By.cssSelector("input[type='password']"),
                By.cssSelector("input[name='password']"),
                By.cssSelector("input[id*='password' i]"),
                By.cssSelector("input[placeholder*='password' i]")
        );
    }

    public void submit() {
        clickFirst("Click Sign In",
                By.cssSelector("button[type='submit']"),
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'login')]"),
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'sign in')]")
        );
    }

    public boolean isLoaded() {
        return firstVisible(
                By.cssSelector("input[type='email']"),
                By.cssSelector("input[type='password']"),
                By.cssSelector("input[name='email']"),
                By.cssSelector("input[name='username']")
        ).isDisplayed();
    }

    public void openForgotPassword() {
        if (DemoConfig.isEnabled()) {
            System.out.println("Open forgot password page");
        }
        clickFirst("Click Forgot Password",
                By.cssSelector("a[href*='forgot']"),
                By.xpath("//*[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'forgot password')]")
        );
    }

    public boolean hasAuthErrorOrValidation() {
        String text = bodyText().toLowerCase();
        return text.contains("invalid")
                || text.contains("required")
                || text.contains("valid email")
                || text.contains("credentials")
                || text.contains("error")
                || driver.getCurrentUrl().toLowerCase().contains("login");
    }

    public String getToken() {
        return localStorageValue("token");
    }
}
