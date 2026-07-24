package io.agentflow.execution.record;

import io.agentflow.model.ModelRequest;
import io.agentflow.model.invocation.ModelInvocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExecutionRecordTest {

    @Test
    void shouldStoreModelInvocation() {

        ExecutionRecord record =
                new ExecutionRecord(
                        "execution-001"
                );


        ModelInvocation invocation =
                new ModelInvocation(
                        "invocation-001",
                        "execution-001",
                        new ModelRequest(
                                "system",
                                "input"
                        )
                );


        record.addModelInvocation(
                invocation
        );


        assertEquals(
                1,
                record.modelInvocations().size()
        );


        assertEquals(
                invocation,
                record.lastModelInvocation()
        );
    }
}