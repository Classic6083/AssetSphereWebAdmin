package com.assetsphere.pages;

import com.assetsphere.config.DemoConfig;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForgotPasswordPage extends BasePage {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public ForgotPasswordPage(WebDriver driver, int timeoutSeconds) {
        super(driver, timeoutSeconds);
    }

    public void open(String baseUrl) {
        if (DemoConfig.isEnabled()) {
            System.out.println("Open forgot password page");
        }
        driver.get(baseUrl.endsWith("/") ? baseUrl + "forgot-password" : baseUrl + "/forgot-password");
        demoPause("Forgot password page loaded");
    }

    public void enterEmail(String email) {
        typeFirst("Enter recovery email", email,
                By.cssSelector("input[type='email']"),
                By.cssSelector("input[name='email']"),
                By.cssSelector("input[id*='email' i]"),
                By.cssSelector("input[placeholder*='email' i]"),
                By.cssSelector("input[type='text']"),
                By.xpath("//label[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'email')]/following::input[1]"),
                By.xpath("(//input[not(@type='hidden') and not(@type='password')])[1]")
        );
    }

    public void sendRecoveryOtp() {
        clickFirst("Click Send Recovery OTP",
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'send recovery otp')]"),
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'send otp')]"),
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'recovery otp')]"),
                By.cssSelector("button[type='submit']")
        );
    }

    public void enterOtp(String otp) {
        List<WebElement> splitOtpInputs = driver.findElements(By.xpath("//input[not(@type='hidden') and (string-length(@maxlength)=0 or number(@maxlength) <= 1)]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .filter(element -> !"email".equalsIgnoreCase(element.getAttribute("type")))
                .filter(element -> !"password".equalsIgnoreCase(element.getAttribute("type")))
                .toList();
        if (splitOtpInputs.size() >= otp.length()) {
            for (int index = 0; index < otp.length(); index++) {
                WebElement element = splitOtpInputs.get(index);
                int digitIndex = index;
                demoInteract("Enter OTP digit " + (digitIndex + 1), element, () -> element.sendKeys(String.valueOf(otp.charAt(digitIndex))));
            }
            return;
        }

        typeFirst("Enter static OTP", otp,
                By.cssSelector("input[name*='otp' i]"),
                By.cssSelector("input[id*='otp' i]"),
                By.cssSelector("input[placeholder*='otp' i]"),
                By.cssSelector("input[inputmode='numeric']"),
                By.cssSelector("input[type='number']"),
                By.xpath("//input[contains(translate(@placeholder,'" + UPPERCASE + "','" + LOWERCASE + "'),'code')]"),
                By.xpath("//label[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'otp')]/following::input[1]"),
                By.xpath("//label[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'code')]/following::input[1]"),
                By.xpath("(//input[not(@type='hidden') and not(@type='email') and not(@type='password')])[1]")
        );
    }

    public void enterNewPassword(String password) {
        typeFirst("Enter new password", password,
                By.cssSelector("input[name*='newPassword' i]"),
                By.cssSelector("input[id*='newPassword' i]"),
                By.cssSelector("input[placeholder*='new password' i]"),
                By.xpath("(//input[@type='password'])[1]")
        );
    }

    public void verifyOtpIfRequired() {
        try {
            clickFirst("Verify OTP",
                    By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'verify otp')]"),
                    By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'verify')]"),
                    By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'submit otp')]"),
                    By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'continue')]"),
                    By.cssSelector("button[type='submit']")
            );
        } catch (AssertionError ignored) {
            demoPause("OTP verify button not shown");
        }
    }

    public void confirmNewPassword(String password) {
        typeFirst("Confirm new password", password,
                By.cssSelector("input[name*='confirm' i]"),
                By.cssSelector("input[id*='confirm' i]"),
                By.cssSelector("input[placeholder*='confirm' i]"),
                By.xpath("(//input[@type='password'])[2]")
        );
    }

    public void submitPasswordReset() {
        clickFirst("Submit password reset",
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'reset password')]"),
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'change password')]"),
                By.xpath("//button[contains(translate(normalize-space(.),'" + UPPERCASE + "','" + LOWERCASE + "'),'submit')]"),
                By.cssSelector("button[type='submit']")
        );
    }

    public void resetPasswordWithStaticOtp(String email, String otp, String newPassword) {
        enterEmail(email);
        sendRecoveryOtp();
        enterOtp(otp);
        verifyOtpIfRequired();
        enterNewPassword(newPassword);
        confirmNewPassword(newPassword);
        submitPasswordReset();
    }

    public boolean hasValidEmailRequiredValidation() {
        String text = bodyText().toLowerCase();
        return text.contains("valid email is required")
                || text.contains("email is required")
                || text.contains("valid email")
                || text.contains("required");
    }

    public boolean isOtpStepVisible() {
        String text = bodyText().toLowerCase();
        return text.contains("otp")
                || text.contains("verification")
                || text.contains("code")
                || isAnyVisible(
                        By.cssSelector("input[name*='otp' i]"),
                        By.cssSelector("input[id*='otp' i]"),
                        By.cssSelector("input[placeholder*='otp' i]")
                );
    }

    public boolean isPasswordResetAccepted() {
        String text = bodyText().toLowerCase();
        String url = driver.getCurrentUrl().toLowerCase();
        return text.contains("password reset")
                || text.contains("password changed")
                || text.contains("success")
                || text.contains("login")
                || url.contains("login");
    }

    public boolean hasInvalidOrExpiredOtpError() {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "INVALID OR EXPIRED OTP"))
                    || wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Invalid OTP"));
        } catch (RuntimeException ignored) {
            String text = bodyText().toLowerCase();
            return text.contains("invalid or expired otp")
                    || text.contains("invalid otp")
                    || text.contains("expired otp")
                    || text.contains("invalid code")
                    || text.contains("expired code");
        }
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
