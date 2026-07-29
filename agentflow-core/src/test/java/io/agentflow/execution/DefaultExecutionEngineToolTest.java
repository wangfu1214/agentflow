package io.agentflow.execution;


import io.agentflow.execution.environment.DefaultExecutionEnvironment;
import io.agentflow.execution.environment.ExecutionEnvironment;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.NoopExecutionLifecycle;
import io.agentflow.execution.pipeline.DefaultExecutionPipeline;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.pipeline.ToolExecutionStep;
import io.agentflow.execution.result.DefaultExecutionResultHandler;
import io.agentflow.tool.ToolDefinition;
import io.agentflow.tool.ToolInvoker;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DefaultExecutionEngineToolTest {

    private static final ExecutionDefinition DEFINITION =
            new ExecutionDefinition(
                    "assistant",
                    "You are helpful.",
                    "query order"
            );

    @Test
    void shouldExecuteToolAndCompleteExecution() {

        ToolDefinition toolDefinition =
                new ToolDefinition(
                        "order-query",
                        "query order");

        ToolInvoker toolInvoker =
                (definition, arguments) ->
                        "order-result";

        ToolExecutionStep toolStep =
                new ToolExecutionStep(
                        toolDefinition,
                        toolInvoker);

        ExecutionEngine engine = createEngine(toolStep);

        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION);

        ExecutionResult result =
                engine.execute(execution);

        assertEquals(
                "order-result",
                result.content());

        assertEquals(
                ExecutionStatus.SUCCEEDED,
                execution.status());
    }

    @Test
    void shouldFailExecutionWhenToolFails() {

        ToolDefinition toolDefinition =
                new ToolDefinition(
                        "order-query",
                        "query order");

        ToolInvoker toolInvoker =
                (definition, arguments) -> {

                    throw new RuntimeException("tool unavailable");
                };

        ExecutionEngine engine =
                createEngine(
                        new ToolExecutionStep(
                                toolDefinition,
                                toolInvoker));

        Execution execution =
                new Execution("execution-001", DEFINITION);

        RuntimeException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        RuntimeException.class,
                        () -> engine.execute(execution));

        assertEquals(
                "tool unavailable",
                exception.getMessage());

        assertEquals(
                ExecutionStatus.FAILED,
                execution.status());
    }


    private ExecutionEngine createEngine(ToolExecutionStep step) {

        ExecutionPipeline pipeline =
                new DefaultExecutionPipeline(List.of(step));

        ExecutionEnvironment environment =
                new DefaultExecutionEnvironment(
                        new DefaultExecutionContextFactory(),
                        new NoopExecutionLifecycle(),
                        new ExecutionInterceptorChain(
                                List.of()
                        ),
                        pipeline,
                        new DefaultExecutionResultHandler());
        return new DefaultExecutionEngine(environment);
    }

}