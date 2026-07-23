package io.agentflow.execution.pipeline;

import io.agentflow.execution.DefaultExecutionContext;
import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionContextKeys;
import io.agentflow.execution.ExecutionDefinition;
import io.agentflow.model.ModelResponse;
import io.agentflow.model.invocation.ModelInvocation;
import io.agentflow.model.invocation.ModelInvocationStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModelExecutionStepTest {

    private static final ExecutionDefinition DEFINITION =
            new ExecutionDefinition(
                    "assistant",
                    "You are helpful.",
                    "Hello"
            );

    @Test
    void shouldCompleteModelInvocation() {
        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );

        ExecutionContext context =
                new DefaultExecutionContext(
                        execution
                );

        ModelExecutionStep step =
                new ModelExecutionStep(
                        request -> {
                            assertEquals(
                                    "You are helpful.",
                                    request.systemPrompt()
                            );
                            assertEquals(
                                    "Hello",
                                    request.input()
                            );

                            return ModelResponse.of(
                                    "Hello from model"
                            );
                        },
                        () -> "invocation-001"
                );

        step.execute(context);

        ModelInvocation invocation =
                (ModelInvocation) context.get(
                        ExecutionContextKeys.MODEL_INVOCATION
                );

        assertEquals(
                "invocation-001",
                invocation.id()
        );
        assertEquals(
                "execution-001",
                invocation.executionId()
        );
        assertEquals(
                ModelInvocationStatus.SUCCEEDED,
                invocation.status()
        );
        assertEquals(
                "Hello from model",
                invocation.response().content()
        );
    }

    @Test
    void shouldMarkInvocationFailedWhenModelFails() {
        RuntimeException modelFailure =
                new RuntimeException(
                        "model unavailable"
                );

        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );

        ExecutionContext context =
                new DefaultExecutionContext(
                        execution
                );

        ModelExecutionStep step =
                new ModelExecutionStep(
                        request -> {
                            throw modelFailure;
                        },
                        () -> "invocation-001"
                );

        RuntimeException thrown =
                assertThrows(
                        RuntimeException.class,
                        () -> step.execute(context)
                );

        ModelInvocation invocation =
                (ModelInvocation) context.get(
                        ExecutionContextKeys.MODEL_INVOCATION
                );

        assertEquals(modelFailure, thrown);
        assertEquals(
                ModelInvocationStatus.FAILED,
                invocation.status()
        );
        assertEquals(
                modelFailure,
                invocation.failure()
        );
    }

    @Test
    void shouldRejectNullModelResponse() {
        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );

        ExecutionContext context =
                new DefaultExecutionContext(
                        execution
                );

        ModelExecutionStep step =
                new ModelExecutionStep(
                        request -> null,
                        () -> "invocation-001"
                );

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> step.execute(context)
                );

        ModelInvocation invocation =
                (ModelInvocation) context.get(
                        ExecutionContextKeys.MODEL_INVOCATION
                );

        assertEquals(
                "modelInvoker returned null response",
                exception.getMessage()
        );
        assertEquals(
                ModelInvocationStatus.FAILED,
                invocation.status()
        );
        assertEquals(
                exception,
                invocation.failure()
        );
    }
}