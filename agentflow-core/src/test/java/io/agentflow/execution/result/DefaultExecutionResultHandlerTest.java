package io.agentflow.execution.result;

import io.agentflow.execution.DefaultExecutionContext;
import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionDefinition;
import io.agentflow.execution.ExecutionResult;
import io.agentflow.model.ModelRequest;
import io.agentflow.model.ModelResponse;
import io.agentflow.model.invocation.ModelInvocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultExecutionResultHandlerTest {

    private static final ExecutionDefinition DEFINITION =
            new ExecutionDefinition(
                    "assistant",
                    "You are helpful.",
                    "Hello"
            );

    @Test
    void shouldBuildResultFromSuccessfulInvocation() {
        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );

        ExecutionContext context =
                new DefaultExecutionContext(
                        execution
                );

        ModelInvocation invocation =
                new ModelInvocation(
                        "invocation-001",
                        execution.id(),
                        new ModelRequest(
                                DEFINITION.systemPrompt(),
                                DEFINITION.input()
                        )
                );

        invocation.start();
        invocation.succeed(
                ModelResponse.of(
                        "Hello from model"
                )
        );

        context.record().addModelInvocation(invocation);

        ExecutionResultHandler handler =
                new DefaultExecutionResultHandler();

        ExecutionResult result =
                handler.handle(context);

        assertEquals(
                "Hello from model",
                result.content()
        );
    }

    @Test
    void shouldRejectMissingModelInvocation() {
        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );

        ExecutionContext context =
                new DefaultExecutionContext(
                        execution
                );

        ExecutionResultHandler handler =
                new DefaultExecutionResultHandler();

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> handler.handle(context)
                );

        assertEquals(
                "modelInvocation not found",
                exception.getMessage()
        );
    }
}