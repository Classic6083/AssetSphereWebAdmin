package com.assetsphere.tests;

import com.assetsphere.config.TestConfig;
import com.assetsphere.config.DemoConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseWebTest {
    protected WebDriver driver;
    protected int timeoutSeconds;

    @BeforeMethod
    public void setUp() {
        timeoutSeconds = TestConfig.getInt("timeoutSeconds");
        String browser = TestConfig.get("browser");
        if (!"chrome".equalsIgnoreCase(browser)) {
            throw new IllegalArgumentException("Only Chrome is configured in this starter project");
        }

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (TestConfig.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            if (DemoConfig.isEnabled() && DemoConfig.holdBrowserOpenSeconds() > 0) {
                try {
                    Thread.sleep(DemoConfig.holdBrowserOpenSeconds() * 1000L);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
            driver.quit();
        }
    }
}
