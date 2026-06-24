package com.assetsphere.tests.mock;

import com.assetsphere.mockflow.MockFlowDefinition;
import com.assetsphere.mockflow.MockFlowEngine;
import com.assetsphere.mockflow.MockFlowResult;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class WebMockUserFlowCoverageTest {
    private final MockFlowEngine engine = new MockFlowEngine();

    @DataProvider(name = "webFlows")
    public Object[][] webFlows() {
        return new Object[][]{
                {new MockFlowDefinition("web-auth-login", "Authentication", "/login", List.of("Open login", "Enter admin credentials", "Submit sign in", "Land on dashboard"), "Dashboard visible")},
                {new MockFlowDefinition("web-auth-forgot-password", "Authentication", "/forgot-password", List.of("Open forgot password", "Enter registered email", "Send reset link"), "Reset link requested")},
                {new MockFlowDefinition("web-auth-set-password", "Authentication", "/set-password", List.of("Open set password", "Enter new password", "Confirm password", "Submit"), "Password reset")},
                {new MockFlowDefinition("web-dashboard-main", "Dashboard", "/", List.of("Load KPI cards", "Load charts", "Open global header"), "Dashboard rendered")},
                {new MockFlowDefinition("web-dashboard-designer", "Dashboard", "/dashboard/designer", List.of("Open designer", "Drag widget", "Resize widget", "Save layout"), "Dashboard layout saved")},
                {new MockFlowDefinition("web-assets-single-add", "Assets", "/assets/add", List.of("Open add asset", "Select category", "Enter serial", "Save asset"), "Asset created")},
                {new MockFlowDefinition("web-assets-bulk-onboarding", "Assets", "/assets/bulk-onboarding", List.of("Upload invoice", "Run OCR", "Verify line items", "Finalize assets"), "Bulk onboarding completed")},
                {new MockFlowDefinition("web-employees-profile-history", "Employees", "/employees/:id", List.of("Open employee roster", "Open details", "Review allocation history", "Review attendance log"), "Employee profile reviewed")},
                {new MockFlowDefinition("web-employees-attendance", "Employees", "/employees/:id/attendance/:date", List.of("Open attendance detail", "View punch-in", "View punch-out", "View total hours"), "Attendance detail reviewed")},
                {new MockFlowDefinition("web-requests-submit", "Requests", "/requests/add", List.of("Open new request", "Choose request type", "Pick asset or category", "Submit request"), "Request submitted")},
                {new MockFlowDefinition("web-requests-process", "Requests", "/requests/process/:id", List.of("Open process request", "Select asset", "Choose condition", "Approve or reject"), "Request processed")},
                {new MockFlowDefinition("web-requests-bulk-approval", "Requests", "/requests", List.of("Select pending requests", "Approve selected", "Confirm notes"), "Bulk approval completed")},
                {new MockFlowDefinition("web-inquiry-create", "Inquiry", "/rfq/create", List.of("Open inquiry", "Add line items", "Assign supplier", "Send inquiry"), "Inquiry dispatched")},
                {new MockFlowDefinition("web-purchase-order", "Purchase Orders", "/purchase-orders/add", List.of("Open PO form", "Add items", "Calculate totals", "Save and print"), "Purchase order prepared")},
                {new MockFlowDefinition("web-pmo-projects-tasks", "PMO", "/pmo/projects", List.of("Open projects", "Create project", "Open tasks", "Move task status"), "PMO board updated")},
                {new MockFlowDefinition("web-pmo-leaves", "Leaves", "/pmo/leaves", List.of("Open leave roster", "Approve leave", "Reject leave", "Open calendar"), "Leave roster processed")},
                {new MockFlowDefinition("web-masters", "Masters", "/masters", List.of("Open master list", "Edit office", "Edit department", "Save lookup"), "Master data maintained")}
        };
    }

    @Test(dataProvider = "webFlows")
    public void allWebUserFlowsAreRepresented(MockFlowDefinition definition) {
        MockFlowResult result = engine.run(definition);

        Assert.assertEquals(result.id(), definition.id());
        Assert.assertEquals(result.executedSteps(), definition.steps().size());
        Assert.assertTrue(result.trace().contains(definition.route()), "Trace should mention the route");
        Assert.assertTrue(result.trace().contains(definition.area()), "Trace should mention the area");
        Assert.assertFalse(definition.steps().isEmpty(), "Each flow should define visible user actions");
    }
}
