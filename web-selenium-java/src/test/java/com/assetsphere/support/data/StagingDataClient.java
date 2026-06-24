package com.assetsphere.support.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class StagingDataClient {
    private final HttpClient client = HttpClient.newHttpClient();

    public String readFixture(String classpathResource) {
        try (InputStream input = StagingDataClient.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (input == null) {
                throw new IllegalStateException("Fixture not found: " + classpathResource);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read fixture: " + classpathResource, exception);
        }
    }

    public int postJson(String url, String jsonBody) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("Failed to call staging endpoint: " + url, exception);
        }
    }

    public StagingApiResponse mockResponse(StagingScenario scenario) {
        String body = readFixture("staging/mock-responses/" + scenario.key() + ".json");
        int statusCode = extractInt(body, "statusCode", 200);
        String message = extractString(body, "message", "Mock response for " + scenario.key());
        return new StagingApiResponse(statusCode, message, body);
    }

    private int extractInt(String json, String key, int fallback) {
        String marker = "\"" + key + "\":";
        int index = json.indexOf(marker);
        if (index < 0) {
            return fallback;
        }
        int start = index + marker.length();
        int end = start;
        while (end < json.length() && Character.isDigit(json.charAt(end))) {
            end++;
        }
        try {
            return Integer.parseInt(json.substring(start, end).trim());
        } catch (RuntimeException ignored) {
            return fallback;
        }
    }

    private String extractString(String json, String key, String fallback) {
        String marker = "\"" + key + "\":";
        int index = json.indexOf(marker);
        if (index < 0) {
            return fallback;
        }
        int startQuote = json.indexOf('"', index + marker.length());
        int endQuote = json.indexOf('"', startQuote + 1);
        if (startQuote < 0 || endQuote < 0) {
            return fallback;
        }
        return json.substring(startQuote + 1, endQuote);
    }
}
