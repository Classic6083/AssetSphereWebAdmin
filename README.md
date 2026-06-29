# Asset Sphere Automation

This workspace contains two starter automation projects:

- `web-selenium-java` - Selenium Java + TestNG for `https://asm.varnueai.com`
- `mobile-appium-java` - Appium Java + TestNG starter for the mobile app

Open either folder directly in Eclipse or VS Code as a Maven project.

## Quick Start

Prerequisites:

- Java JDK 17 or newer
- Maven
- Google Chrome
- For mobile: Node.js, Appium 2, Android Studio, Android SDK, and an emulator or real Android device

Run web tests:

```powershell
cd web-selenium-java
mvn test
```

Run mobile tests after configuring the app/device details:

```powershell
cd mobile-appium-java
mvn test
```

The credentials and environment values live in each project's `src/test/resources/config.properties`.

