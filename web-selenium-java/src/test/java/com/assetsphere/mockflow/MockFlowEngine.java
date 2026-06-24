package com.assetsphere.mockflow;

public class MockFlowEngine {
    public MockFlowResult run(MockFlowDefinition definition) {
        StringBuilder trace = new StringBuilder();
        trace.append(definition.area()).append(" -> ").append(definition.route()).append('\n');
        for (String step : definition.steps()) {
            trace.append(step).append('\n');
            System.out.println(definition.id() + " :: " + step);
        }
        return new MockFlowResult(
                definition.id(),
                definition.steps().size(),
                definition.expectedOutcome(),
                trace.toString()
        );
    }
}
