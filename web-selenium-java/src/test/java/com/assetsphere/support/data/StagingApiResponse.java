package com.assetsphere.support.data;

public class StagingApiResponse {
    private final int statusCode;
    private final String message;
    private final String body;

    public StagingApiResponse(int statusCode, String message, String body) {
        this.statusCode = statusCode;
        this.message = message;
        this.body = body;
    }

    public int statusCode() {
        return statusCode;
    }

    public String message() {
        return message;
    }

    public String body() {
        return body;
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }
}
