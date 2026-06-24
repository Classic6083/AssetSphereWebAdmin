package com.assetsphere.mockflow;

import java.util.List;

public record MockFlowDefinition(
        String id,
        String area,
        String route,
        List<String> steps,
        String expectedOutcome
) {
}
