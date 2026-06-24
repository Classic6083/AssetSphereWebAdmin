package com.assetsphere.tests;

import com.assetsphere.config.TestConfig;
import com.assetsphere.pages.LoginPage;
import com.assetsphere.pages.ProtectedModulePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class WebModuleRouteSmokeTest extends BaseWebTest {

    @Test
    public void documentedProtectedRoutesLoadForAdmin() {
        LoginPage loginPage = new LoginPage(driver, timeoutSeconds);
        loginPage.open(TestConfig.get("baseUrl"));
        loginPage.login(TestConfig.get("admin.email"), TestConfig.get("admin.password")).isDisplayed();

        ProtectedModulePage modulePage = new ProtectedModulePage(driver, timeoutSeconds);
        Map<String, String> routes = new LinkedHashMap<>();
        routes.put("/assets", "asset");
        routes.put("/assets/add", "asset");
        routes.put("/assets/bulk-onboarding", "bulk");
        routes.put("/employees", "employee");
        routes.put("/requests", "request");
        routes.put("/requests/add", "request");
        routes.put("/purchase-orders", "purchase");
        routes.put("/rfq/create", "inquiry");
        routes.put("/pmo/projects", "project");
        routes.put("/pmo/tasks", "task");
        routes.put("/pmo/leaves", "leave");
        routes.put("/masters", "master");

        routes.forEach((path, keyword) -> {
            modulePage.open(TestConfig.get("baseUrl"), path);
            Assert.assertTrue(modulePage.isAvailable(keyword), "Protected route should load: " + path);
        });
    }
}
