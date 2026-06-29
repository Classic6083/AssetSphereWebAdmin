package com.assetsphere.tests;

import com.assetsphere.config.TestConfig;
import com.assetsphere.pages.ForgotPasswordPage;
import com.assetsphere.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebAuthValidationTest extends BaseWebTest {

    @Test
    public void invalidEmailFormatDoesNotAuthenticate() {
        LoginPage loginPage = new LoginPage(driver, timeoutSeconds);
        loginPage.open(TestConfig.get("baseUrl"));

        loginPage.enterEmail("not-an-email");
        loginPage.enterPassword("wrong-password");
        loginPage.submit();

        Assert.assertTrue(loginPage.hasAuthErrorOrValidation(), "Invalid email should keep the user on login or show validation");
        Assert.assertNull(loginPage.getToken(), "Invalid login must not create an auth token");
    }

    @Test
    public void wrongPasswordShowsAuthenticationError() {
        LoginPage loginPage = new LoginPage(driver, timeoutSeconds);
        loginPage.open(TestConfig.get("baseUrl"));

        loginPage.enterEmail(TestConfig.get("admin.email"));
        loginPage.enterPassword("WrongPassword#123");
        loginPage.submit();

        Assert.assertTrue(loginPage.hasAuthErrorOrValidation(), "Wrong password should show an authentication error");
        Assert.assertNull(loginPage.getToken(), "Failed login must not create an auth token");
    }

    @Test
    public void forgotPasswordPageLoads() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver, timeoutSeconds);
        forgotPasswordPage.open(TestConfig.get("baseUrl"));

        Assert.assertTrue(forgotPasswordPage.isLoaded(), "Forgot password page should be available from public routes");
    }

    @Test
    public void forgotPasswordRequiresValidEmail() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver, timeoutSeconds);
        forgotPasswordPage.open(TestConfig.get("baseUrl"));

        forgotPasswordPage.sendRecoveryOtp();

        Assert.assertTrue(
                forgotPasswordPage.hasValidEmailRequiredValidation(),
                "Forgot password should show 'Valid email is required' when email is not provided"
        );
    }

    @Test
    public void forgotPasswordShowsErrorForStaticOtp() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver, timeoutSeconds);
        forgotPasswordPage.open(TestConfig.get("baseUrl"));

        forgotPasswordPage.enterEmail(TestConfig.get("employee.email"));
        forgotPasswordPage.sendRecoveryOtp();

        Assert.assertTrue(forgotPasswordPage.isOtpStepVisible(), "OTP step should be visible after sending recovery OTP");

        forgotPasswordPage.enterOtp(TestConfig.get("forgotPassword.staticOtp"));
        forgotPasswordPage.verifyOtpIfRequired();

        Assert.assertTrue(
                forgotPasswordPage.hasInvalidOrExpiredOtpError(),
                "Static OTP should show invalid or expired OTP error message"
        );
    }
}
