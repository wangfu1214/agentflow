package io.agentflow.model.invocation;

import io.agentflow.execution.invocation.RuntimeInvocationStatus;
import io.agentflow.model.ModelRequest;
import io.agentflow.model.ModelResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModelInvocationTest {

    private static final ModelRequest REQUEST =
            new ModelRequest(
                    "You are helpful.",
                    "Hello"
            );

    @Test
    void shouldCreateInvocationInCreatedStatus() {
        ModelInvocation invocation =
                new ModelInvocation(
                        "invocation-001",
                        "execution-001",
                        REQUEST
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
                REQUEST,
                invocation.request()
        );
        assertEquals(
                RuntimeInvocationStatus.CREATED,
                invocation.status()
        );
        assertNull(invocation.response());
        assertNull(invocation.failure());
    }

    @Test
    void shouldCompleteSuccessfulLifecycle() {
        ModelInvocation invocation =
                new ModelInvocation(
                        "invocation-001",
                        "execution-001",
                        REQUEST
                );

        ModelResponse response =
                ModelResponse.of("Hello");

        invocation.start();
        invocation.succeed(response);

        assertEquals(
                RuntimeInvocationStatus.SUCCEEDED,
                invocation.status()
        );
        assertEquals(
                response,
                invocation.response()
        );
        assertNull(invocation.failure());
    }

    @Test
    void shouldCompleteFailedLifecycle() {
        ModelInvocation invocation =
                new ModelInvocation(
                        "invocation-001",
                        "execution-001",
                        REQUEST
                );

        RuntimeException failure =
                new RuntimeException(
                        "model unavailable"
                );

        invocation.start();
        invocation.fail(failure);

        assertEquals(
                RuntimeInvocationStatus.FAILED,
                invocation.status()
        );
        assertEquals(
                failure,
                invocation.failure()
        );
        assertNull(invocation.response());
    }

    @Test
    void shouldRejectSuccessBeforeStart() {
        ModelInvocation invocation =
                new ModelInvocation(
                        "invocation-001",
                        "execution-001",
                        REQUEST
                );

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> invocation.succeed(
                                ModelResponse.of("done")
                        )
                );

        assertEquals(
                "expected model invocation status "
                        + "RUNNING but was CREATED",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectStartingInvocationTwice() {
        ModelInvocation invocation =
                new ModelInvocation(
                        "invocation-001",
                        "execution-001",
                        REQUEST
                );

        invocation.start();

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        invocation::start
                );

        assertEquals(
                "expected model invocation status "
                        + "CREATED but was RUNNING",
                exception.getMessage()
        );
    }
}