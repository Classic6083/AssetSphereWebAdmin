package com.assetsphere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.assetsphere.config.DemoConfig;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isDisplayed() {
        ExpectedCondition<Boolean> dashboardSignal = webDriver -> {
            String url = driver.getCurrentUrl().toLowerCase();
            String body = driver.findElement(By.tagName("body")).getText().toLowerCase();
            return url.contains("dashboard")
                    || body.contains("dashboard")
                    || body.contains("asset")
                    || body.contains("logout")
                    || body.contains("sign out");
        };
        boolean displayed = wait.until(dashboardSignal);
        if (displayed && DemoConfig.isEnabled()) {
            System.out.println("Dashboard visible");
        }
        return displayed;
    }

    public boolean hasRequirementKpis() {
        ExpectedCondition<Boolean> kpiSignal = webDriver -> {
            String body = driver.findElement(By.tagName("body")).getText().toLowerCase();
            int matches = 0;
            String[] expectedLabels = {
                    "total assets",
                    "active users",
                    "available assets",
                    "maintenance",
                    "pending requests",
                    "asset value"
            };

            for (String label : expectedLabels) {
                if (body.contains(label)) {
                    matches++;
                }
            }
            return matches >= 3;
        };
        boolean found = wait.until(kpiSignal);
        if (found && DemoConfig.isEnabled()) {
            System.out.println("Dashboard KPI check complete");
        }
        return found;
    }
}
