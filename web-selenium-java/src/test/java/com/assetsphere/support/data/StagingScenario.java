package com.assetsphere.support.data;

public enum StagingScenario {
    ASSET_ADD_EDIT("asset-add-edit", "/assets/add", "asset", "staging/fixtures/asset-add-edit-seed.json"),
    BULK_OCR_ONBOARDING("bulk-ocr-onboarding", "/assets/bulk-onboarding", "bulk", "staging/fixtures/bulk-ocr-seed.json"),
    BARCODE_DUPLICATE_SCAN("barcode-duplicate-scan", "/assets", "serial", "staging/fixtures/barcode-seed.json"),
    REQUEST_APPROVAL_REJECTION("request-approval-rejection", "/requests", "request", "staging/fixtures/request-approval-seed.json"),
    PURCHASE_ORDER("purchase-order", "/purchase-orders/add", "purchase", "staging/fixtures/purchase-order-seed.json"),
    INQUIRY_EMAIL_DISPATCH("inquiry-email-dispatch", "/rfq/create", "inquiry", "staging/fixtures/inquiry-seed.json"),
    LEAVE_APPROVAL_REJECTION("leave-approval-rejection", "/pmo/leaves", "leave", "staging/fixtures/leave-seed.json"),
    SUPPLIER_VALIDATION("supplier-validation", "/masters", "supplier", "staging/fixtures/supplier-seed.json");

    private final String key;
    private final String path;
    private final String pageKeyword;
    private final String seedFixture;

    StagingScenario(String key, String path, String pageKeyword, String seedFixture) {
        this.key = key;
        this.path = path;
        this.pageKeyword = pageKeyword;
        this.seedFixture = seedFixture;
    }

    public String key() {
        return key;
    }

    public String path() {
        return path;
    }

    public String pageKeyword() {
        return pageKeyword;
    }

    public String seedFixture() {
        return seedFixture;
    }
}
