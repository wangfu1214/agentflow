package io.agentflow.execution.record;

import java.time.Instant;

public record ExecutionEvent(String name, Instant timestamp) {
}
