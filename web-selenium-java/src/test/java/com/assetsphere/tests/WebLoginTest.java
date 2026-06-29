package com.assetsphere.tests;

import com.assetsphere.config.DemoConfig;
import com.assetsphere.config.TestConfig;
import com.assetsphere.pages.DashboardPage;
import com.assetsphere.pages.LoginPage;
import com.assetsphere.pages.ProtectedModulePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebLoginTest extends BaseWebTest {

    @Test
    public void adminCanLogin() {
        LoginPage loginPage = new LoginPage(driver, timeoutSeconds);
        loginPage.open(TestConfig.get("baseUrl"));

        DashboardPage dashboardPage = loginPage.login(
                TestConfig.get("admin.email"),
                TestConfig.get("admin.password")
        );

        Assert.assertTrue(dashboardPage.isDisplayed(), "Admin should reach the dashboard after login");
        //Assert.assertNotNull(loginPage.getToken(), "Auth token should be stored in localStorage after login");

        if (DemoConfig.isEnabled()) {
            ProtectedModulePage modulePage = new ProtectedModulePage(driver, timeoutSeconds);

            modulePage.open(TestConfig.get("baseUrl"), "/assets");
            Assert.assertTrue(modulePage.isAvailable("asset"), "Assets module should open after login");

            modulePage.open(TestConfig.get("baseUrl"), "/requests");
            Assert.assertTrue(modulePage.isAvailable("request"), "Requests module should open after login");
        }
    }

    @Test
    public void employeeCanLogin() {
        LoginPage loginPage = new LoginPage(driver, timeoutSeconds);
        loginPage.open(TestConfig.get("baseUrl"));

        DashboardPage dashboardPage = loginPage.login(
                TestConfig.get("employee.email"),
                TestConfig.get("employee.password")
        );

        Assert.assertTrue(dashboardPage.isDisplayed(), "Employee should reach the dashboard after login");
        //Assert.assertNotNull(loginPage.getToken(), "Auth token should be stored in localStorage after login");
    }
}
