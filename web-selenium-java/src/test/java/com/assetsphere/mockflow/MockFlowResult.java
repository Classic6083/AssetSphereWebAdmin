package com.assetsphere.mockflow;

public record MockFlowResult(
        String id,
        int executedSteps,
        String finalOutcome,
        String trace
) {
}
