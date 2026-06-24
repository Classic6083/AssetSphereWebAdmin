package com.assetsphere.tests.staging;

import com.assetsphere.config.TestConfig;
import com.assetsphere.pages.DashboardPage;
import com.assetsphere.pages.LoginPage;
import com.assetsphere.pages.ProtectedModulePage;
import com.assetsphere.support.data.StagingDataConfig;
import com.assetsphere.support.data.StagingDataManager;
import com.assetsphere.support.data.StagingApiResponse;
import com.assetsphere.support.data.StagingScenario;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DeferredDataFlowsStagingTest {
    private final StagingDataManager stagingDataManager = new StagingDataManager();

    @DataProvider(name = "stagingScenarios")
    public Object[][] stagingScenarios() {
        return new Object[][]{
                {StagingScenario.ASSET_ADD_EDIT},
                {StagingScenario.BULK_OCR_ONBOARDING},
                {StagingScenario.BARCODE_DUPLICATE_SCAN},
                {StagingScenario.REQUEST_APPROVAL_REJECTION},
                {StagingScenario.PURCHASE_ORDER},
                {StagingScenario.INQUIRY_EMAIL_DISPATCH},
                {StagingScenario.LEAVE_APPROVAL_REJECTION},
                {StagingScenario.SUPPLIER_VALIDATION}
        };
    }

    @Test(groups = "staging-data", dataProvider = "stagingScenarios")
    public void deferredFlowSeedsAndLoadsExpectedRoute(StagingScenario scenario) {
        StagingApiResponse response = stagingDataManager.seed(scenario);
        WebDriver driver = null;
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);

            int timeoutSeconds = StagingDataConfig.requestTimeoutSeconds();
            String baseUrl = StagingDataConfig.baseUrl().isBlank() ? TestConfig.get("baseUrl") : StagingDataConfig.baseUrl();

            LoginPage loginPage = new LoginPage(driver, timeoutSeconds);
            loginPage.open(baseUrl);
            DashboardPage dashboardPage = loginPage.login(TestConfig.get("admin.email"), TestConfig.get("admin.password"));

            Assert.assertTrue(response.isSuccessful(), "Mock or live staging seed should succeed for " + scenario.key());
            Assert.assertTrue(response.body().contains(scenario.key()), "Seed response should mention the scenario key");
            Assert.assertTrue(dashboardPage.isDisplayed(), "Admin should land on the dashboard before exercising staging data flows");

            if (StagingDataConfig.isMockMode()) {
                Assert.assertFalse(response.message().isBlank(), "Mock response should include a readable message");
                return;
            }

            ProtectedModulePage modulePage = new ProtectedModulePage(driver, timeoutSeconds);
            modulePage.open(baseUrl, scenario.path());
            Assert.assertTrue(modulePage.isAvailable(scenario.pageKeyword()), "Staging route should open for " + scenario.key());
        } finally {
            if (driver != null) {
                driver.quit();
            }
            stagingDataManager.cleanup(scenario);
        }
    }
}
