# Web Project Summary

## Project
`web-selenium-java`

This project automates the Asset Sphere admin web app using Selenium Java and TestNG.

## Folder Structure

```text
web-selenium-java/
├── pom.xml
├── testng.xml
├── README.md
└── src
    └── test
        ├── java
        │   └── com
        │       └── assetsphere
        │           ├── config
        │           │   └── TestConfig.java
        │           ├── pages
        │           │   ├── BasePage.java
        │           │   ├── DashboardPage.java
        │           │   ├── ForgotPasswordPage.java
        │           │   ├── LoginPage.java
        │           │   └── ProtectedModulePage.java
        │           └── tests
        │               ├── BaseWebTest.java
        │               ├── WebAuthValidationTest.java
        │               ├── WebDashboardSmokeTest.java
        │               ├── WebLoginTest.java
        │               └── WebModuleRouteSmokeTest.java
        └── resources
            └── config.properties
```

## Responsibility Of Each Part

- `pom.xml`: Maven dependencies and test execution setup.
- `testng.xml`: Test suite definition and class grouping.
- `config.properties`: Environment values like base URL, browser, timeout, and credentials.
- `config/TestConfig.java`: Reads and serves configuration values to tests.
- `pages/*`: Page Object classes that wrap login, dashboard, forgot-password, and route-level interactions.
- `tests/*`: Actual TestNG test cases for login, validations, dashboard smoke, and protected module smoke checks.
- `README.md`: Local run instructions and setup notes.

## What This Project Covers

- Admin login
- Employee login
- Invalid login validation
- Forgot password route smoke
- Dashboard smoke checks
- Protected route smoke for modules like Assets, Employees, Requests, Inquiry, PO, PMO, Leaves, and Masters

## What It Does Not Automate Yet

- Asset creation and bulk onboarding
- Request approval/rejection side effects
- Inquiry dispatch and purchase order generation
- Any workflow that should be run only on controlled test data

