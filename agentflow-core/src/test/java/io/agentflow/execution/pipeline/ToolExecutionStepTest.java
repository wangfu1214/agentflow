package io.agentflow.execution.pipeline;


import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionDefinition;
import io.agentflow.execution.ExecutionStatus;
import io.agentflow.execution.invocation.RuntimeInvocationStatus;
import io.agentflow.execution.record.ExecutionRecord;
import io.agentflow.tool.ToolDefinition;
import io.agentflow.tool.ToolInvoker;
import io.agentflow.tool.invocation.ToolInvocation;

import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ToolExecutionStepTest {


    @Test
    void shouldCompleteToolInvocation() {

        ToolDefinition definition =
                new ToolDefinition(
                        "order-query",
                        "query order information");

        ToolInvoker invoker = (tool, arguments) -> "order-result";

        ToolExecutionStep step =
                new ToolExecutionStep(
                        definition,
                        invoker,
                        () -> "tool-invocation-001");

        Execution execution =
                new Execution(
                        "execution-001",
                        new ExecutionDefinition(
                                "assistant",
                                "system prompt",
                                "hello"));

        TestExecutionContext context = new TestExecutionContext(execution);
        step.execute(context);
        assertEquals(
                1,
                context.record()
                        .invocations()
                        .size());

        ToolInvocation invocation =
                (ToolInvocation)
                        context.record()
                                .invocations()
                                .get(0);

        assertEquals(
                "tool-invocation-001",
                invocation.id());

        assertEquals(
                "order-query",
                invocation.toolName());

        assertEquals(
                RuntimeInvocationStatus.SUCCEEDED,
                invocation.status());

        assertEquals(
                "order-result",
                invocation.result());
    }

    @Test
    void shouldMarkToolInvocationFailedWhenInvokerThrows() {
        ToolDefinition definition =
                new ToolDefinition(
                        "order-query",
                        "query order information");

        ToolInvoker invoker =
                (tool, arguments) -> {
                    throw new RuntimeException("tool failed");
                };

        ToolExecutionStep step =
                new ToolExecutionStep(definition, invoker, () -> "tool-invocation-001");

        Execution execution =
                new Execution(
                        "execution-001",
                        new ExecutionDefinition(
                                "assistant",
                                "system prompt",
                                "hello"));

        TestExecutionContext context = new TestExecutionContext(execution);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> step.execute(context));

        assertEquals(
                "tool failed",
                exception.getMessage());

        ToolInvocation invocation =
                (ToolInvocation)
                        context.record()
                                .invocations()
                                .get(0);

        assertEquals(RuntimeInvocationStatus.FAILED, invocation.status());

        assertEquals("tool failed", invocation.failure().getMessage());

    }



    private static final class TestExecutionContext
            implements ExecutionContext {

        private final Execution execution;

        private final ExecutionRecord record =
                new ExecutionRecord(
                        "execution-001");

        private ExecutionStatus status =
                ExecutionStatus.CREATED;

        private TestExecutionContext(Execution execution) {
            this.execution = execution;
        }

        @Override
        public Execution getExecution() {
            return execution;
        }

        @Override
        public ExecutionStatus getStatus() {
            return status;
        }

        @Override
        public void setStatus(
                ExecutionStatus state
        ) {
            this.status = state;
        }

        @Override
        public ExecutionRecord record() {
            return record;
        }

        @Override
        public Object get(String key) {
            return null;
        }

        @Override
        public void put(String key, Object value) {

        }
    }

}