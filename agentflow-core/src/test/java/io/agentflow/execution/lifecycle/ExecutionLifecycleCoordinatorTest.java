package io.agentflow.execution.lifecycle;

import io.agentflow.execution.*;
import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExecutionLifecycleCoordinatorTest {

    private static final ExecutionDefinition DEFINITION = new ExecutionDefinition(
            "assistant",
            "system",
            "hello",
            List.of());


    @Test
    void shouldCompleteExecutionLifecycle() {

        AtomicBoolean before = new AtomicBoolean(false);

        AtomicBoolean after = new AtomicBoolean(false);

        ExecutionLifecycle lifecycle = new ExecutionLifecycle() {

            @Override
            public void beforeExecute(Execution execution, ExecutionContext context) {
                before.set(true);
            }

            @Override
            public void afterExecute(Execution execution, ExecutionContext context) {
                after.set(true);
            }
        };

        ExecutionPipeline pipeline = context -> {
        };

        ExecutionResultHandler handler = context -> ExecutionResult.of("success");

        ExecutionLifecycleCoordinator coordinator = new ExecutionLifecycleCoordinator(
                lifecycle,
                new ExecutionInterceptorChain(List.of()),
                pipeline,
                handler);

        Execution execution = new Execution("execution-001", DEFINITION);

        ExecutionContext context = new DefaultExecutionContext(execution);

        ExecutionResult result = coordinator.execute(execution, context);

        assertEquals("success", result.content());

        assertTrue(before.get());

        assertTrue(after.get());

        assertEquals(ExecutionStatus.SUCCEEDED, execution.status());
    }

}