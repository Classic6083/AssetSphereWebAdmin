# Asset Sphere Web Automation

Framework:

- Java 17
- Selenium WebDriver
- TestNG
- WebDriverManager
- Maven

## Run

```powershell
mvn test
```

Useful overrides:

```powershell
mvn test -Dheadless=true
mvn test -Dbrowser=chrome -DbaseUrl=https://asm.varnueai.com
```

To watch the browser step through the flow more slowly:

```powershell
mvn test -Dheadless=false -DdemoMode=true -DstepPauseSeconds=3 -DholdBrowserOpenSeconds=10
```

Run the staging-data suite after pointing the staging endpoints at your reset/seed service:

```powershell
mvn test -DsuiteXmlFile=testng-staging-data.xml -Dstaging.enabled=true -Dstaging.seedEndpoint=https://your-staging-host/seed -Dstaging.cleanupEndpoint=https://your-staging-host/cleanup
```

Run the add-request flow visibly:

```powershell
mvn test "-DsuiteXmlFile=testng-request-flow.xml" "-Dheadless=false" "-DdemoMode=true" "-DstepPauseSeconds=3" "-DholdBrowserOpenSeconds=10"
```

## Eclipse Setup

1. Open Eclipse.
2. Select `File > Import > Existing Maven Projects`.
3. Choose the `web-selenium-java` folder.
4. Let Maven download dependencies.
5. Right-click `testng.xml` and run as TestNG Suite.

## What To Add Next

- Asset creation tests
- Employee asset request tests
- Admin approval/rejection tests
- Reports/download validation
- Negative login tests

## Requirement Coverage Added

The suite now covers non-destructive checks from the BRD and Web Admin user-flow document:

- Valid admin and employee login
- Login token/session persistence in local storage
- Invalid email/wrong password authentication validation
- Forgot password route smoke
- Dashboard KPI label smoke checks
- Protected module route smoke checks for Assets, Employees, Requests, Inquiry/RFQ, Purchase Orders, PMO, Leaves, Masters, and related modules

Live data creation workflows such as adding assets, approving requests, creating POs, and sending inquiries are intentionally not automated against production by default.

## Staging Data Track

The deferred BRD flows now have a separate staging-only path:

- Add/edit asset
- Bulk OCR invoice onboarding
- Barcode duplicate scan and auto-focus assertions
- Request approval/rejection side effects
- Purchase order creation and print validation
- Inquiry email dispatch
- Leave approval/rejection
- Supplier GSTIN/CIN creation validation

Those tests are grouped under `staging-data` and use seed/cleanup utilities so they can be aimed at a resettable staging dataset.
