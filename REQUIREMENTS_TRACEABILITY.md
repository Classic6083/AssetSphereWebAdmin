# Asset Sphere Automation Requirement Traceability

Requirement sources:

- `Ams_Webadmin_userflow (1).md`
- `BRD_Asset_Management_System_Comprehensive.md`
- `user_flow_documentMobile_Application (1)`

## Web Admin Coverage

| Requirement Area | Automated Test |
|---|---|
| Valid login redirects to dashboard and stores token | `WebLoginTest.adminCanLogin`, `WebLoginTest.employeeCanLogin` |
| Invalid email / wrong password blocks authentication | `WebAuthValidationTest` |
| Forgot password public route | `WebAuthValidationTest.forgotPasswordPageLoads` |
| Dashboard KPI visibility | `WebDashboardSmokeTest.adminDashboardShowsRequirementKpis` |
| Protected module route structure | `WebModuleRouteSmokeTest.documentedProtectedRoutesLoadForAdmin` |

## Mobile Coverage

| Requirement Area | Automated Test |
|---|---|
| Employee login | `MobileLoginTest.employeeCanLoginOnMobile` |
| Invalid mobile login validation | `MobileAuthenticationFlowTest.invalidEmailDoesNotAuthenticate` |
| Forgot password screen | `MobileAuthenticationFlowTest.forgotPasswordScreenLoads` |
| Home dashboard / quick actions | `MobileEmployeeFlowSmokeTest` |
| Requests tab and new request required-field validation | `MobileEmployeeFlowSmokeTest` |
| Attendance tab smoke | `MobileEmployeeFlowSmokeTest` |
| Profile screen smoke | `MobileEmployeeFlowSmokeTest` |

## Deferred For Safe Test Data

The following BRD/user-flow requirements need a staging dataset or explicit test-data reset before automation should run against a shared environment:

- Add/edit asset
- Bulk OCR invoice onboarding
- Barcode duplicate scan and auto-focus assertions
- Request approval/rejection side effects
- Purchase order creation and print validation
- Inquiry email dispatch
- Leave approval/rejection
- Supplier GSTIN/CIN creation validation

## Mock User-Flow Coverage

The following suites now provide mocked coverage for the full documented user-flow sets:

- Web: `testng-web-userflow-mock.xml`
- Mobile: `testng-mobile-userflow-mock.xml`

These suites do not depend on incomplete live routes or app screens. They validate that each documented flow is represented in the automated coverage model with a route, ordered steps, and expected outcome.
