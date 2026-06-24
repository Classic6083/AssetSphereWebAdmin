package com.assetsphere.tests;

import com.assetsphere.config.DemoConfig;
import com.assetsphere.config.TestConfig;
import com.assetsphere.pages.LoginPage;
import com.assetsphere.pages.RequestAddPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebCreateRequestTest extends BaseWebTest {

    @Test
    public void employeeCanCreateNewRequest() {
        LoginPage loginPage = new LoginPage(driver, timeoutSeconds);
        loginPage.open(TestConfig.get("baseUrl"));

        loginPage.login(
                TestConfig.get("employee.email"),
                TestConfig.get("employee.password")
        );

        RequestAddPage requestAddPage = new RequestAddPage(driver, timeoutSeconds);
        requestAddPage.open(TestConfig.get("baseUrl"));
        Assert.assertTrue(requestAddPage.isLoaded(), "Request add page should be visible");

        requestAddPage.chooseRequestType("new request", "new asset request", "maintenance");
        requestAddPage.chooseCategoryFromFirstOption();
        requestAddPage.enterJustification("Automated request created by the test suite");
        requestAddPage.choosePriority("medium", "high");
        requestAddPage.submit();

        Assert.assertTrue(requestAddPage.isSuccess(), "Request should be created successfully");

        if (DemoConfig.isEnabled()) {
            System.out.println("Request flow completed");
        }
    }
}
