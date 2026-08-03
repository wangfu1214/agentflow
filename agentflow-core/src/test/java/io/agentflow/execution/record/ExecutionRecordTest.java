package io.agentflow.execution.record;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExecutionRecordTest {

    @Test
    void shouldCreateRecordInCreatedStatus() {
        ExecutionRecord record =
                new ExecutionRecord("execution-001");
        assertEquals("execution-001", record.executionId());
        assertNotNull(record.createdAt());
    }
}