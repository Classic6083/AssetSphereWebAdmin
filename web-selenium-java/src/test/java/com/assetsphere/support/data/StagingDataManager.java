package com.assetsphere.support.data;

import org.testng.SkipException;

public class StagingDataManager {
    private final StagingDataClient client = new StagingDataClient();

    public StagingApiResponse seed(StagingScenario scenario) {
        requireReady();
        if (StagingDataConfig.isMockMode()) {
            return client.mockResponse(scenario);
        }
        int status = client.postJson(
                StagingDataConfig.seedEndpoint(),
                client.readFixture(scenario.seedFixture())
        );
        if (status < 200 || status >= 300) {
            throw new IllegalStateException("Seed failed for " + scenario.key() + " with status " + status);
        }
        return new StagingApiResponse(status, "Live seed success for " + scenario.key(), "{\"statusCode\":" + status + "}");
    }

    public void cleanup(StagingScenario scenario) {
        if (StagingDataConfig.isMockMode()) {
            return;
        }
        if (!StagingDataConfig.isEnabled() || StagingDataConfig.cleanupEndpoint().isBlank()) {
            return;
        }
        int status = client.postJson(
                StagingDataConfig.cleanupEndpoint(),
                "{\"scenario\":\"" + scenario.key() + "\"}"
        );
        if (status < 200 || status >= 300) {
            throw new IllegalStateException("Cleanup failed for " + scenario.key() + " with status " + status);
        }
    }

    public void requireReady() {
        if (!StagingDataConfig.isEnabled()) {
            throw new SkipException("Enable staging.enabled=true to run staging-data tests");
        }
        if (!StagingDataConfig.isMockMode() && (StagingDataConfig.seedEndpoint().isBlank() || StagingDataConfig.cleanupEndpoint().isBlank())) {
            throw new SkipException("Configure staging.seedEndpoint and staging.cleanupEndpoint to run staging-data tests");
        }
    }
}
