package io.agentflow.execution;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExecutionTest {

    private static final ExecutionDefinition DEFINITION =
            new ExecutionDefinition(
                    "assistant",
                    "You are helpful.",
                    "Hello",
                    List.of());

    @Test
    void shouldCreateExecutionInCreatedStatus() {
        Execution execution = new Execution("execution-001", DEFINITION);

        assertEquals("execution-001", execution.id());
        assertEquals(DEFINITION, execution.definition());
        assertEquals(
                ExecutionStatus.CREATED,
                execution.status());
    }

    @Test
    void shouldCompleteSuccessfulLifecycle() {
        Execution execution = new Execution("execution-001", DEFINITION);

        execution.start();
        assertEquals(
                ExecutionStatus.RUNNING,
                execution.status());

        execution.succeed();
        assertEquals(
                ExecutionStatus.SUCCEEDED,
                execution.status());
    }

    @Test
    void shouldCompleteFailedLifecycle() {
        Execution execution =
                new Execution("execution-001", DEFINITION);

        execution.start();
        execution.fail();

        assertEquals(
                ExecutionStatus.FAILED,
                execution.status());
    }

    @Test
    void shouldCancelCreatedExecution() {
        Execution execution = new Execution("execution-001", DEFINITION);

        execution.cancel();

        assertEquals(
                ExecutionStatus.CANCELLED,
                execution.status());
    }

    @Test
    void shouldRejectStartingExecutionTwice() {
        Execution execution = new Execution("execution-001", DEFINITION);

        execution.start();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                execution::start);

        assertEquals(
                "expected execution status CREATED but was RUNNING",
                exception.getMessage());
    }

    @Test
    void shouldRejectSucceedingBeforeStart() {
        Execution execution = new Execution("execution-001", DEFINITION);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                execution::succeed);

        assertEquals(
                "expected execution status RUNNING but was CREATED",
                exception.getMessage());
    }

    @Test
    void shouldRejectCancellingCompletedExecution() {
        Execution execution = new Execution("execution-001", DEFINITION);

        execution.start();
        execution.succeed();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                execution::cancel);

        assertEquals(
                "execution cannot be cancelled from status SUCCEEDED",
                exception.getMessage());
    }
}
