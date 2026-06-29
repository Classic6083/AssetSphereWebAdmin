package com.assetsphere.tests;

import com.assetsphere.config.TestConfig;
import com.assetsphere.pages.DashboardPage;
import com.assetsphere.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebDashboardSmokeTest extends BaseWebTest {

    @Test
    public void adminDashboardLoadsAfterLogin() {
        LoginPage loginPage = new LoginPage(driver, timeoutSeconds);
        loginPage.open(TestConfig.get("baseUrl"));

        DashboardPage dashboardPage = loginPage.login(
                TestConfig.get("admin.email"),
                TestConfig.get("admin.password")
        );

        Assert.assertTrue(dashboardPage.isDisplayed(), "Admin should land on the protected dashboard after login");
        //Assert.assertNotNull(loginPage.getToken(), "Admin login should persist an auth token");
    }
}
