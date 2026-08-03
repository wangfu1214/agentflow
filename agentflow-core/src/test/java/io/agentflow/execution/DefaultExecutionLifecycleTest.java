package io.agentflow.execution;

import io.agentflow.execution.lifecycle.ExecutionLifecycle;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultExecutionLifecycleTest {

    private static final ExecutionDefinition DEFINITION =
            new ExecutionDefinition(
                    "assistant",
                    "You are helpful.",
                    "Hello",
                    List.of()
            );

    @Test
    void shouldInvokeBeforeAndAfterLifecycle() {

        AtomicBoolean beforeCalled = new AtomicBoolean(false);

        AtomicBoolean afterCalled = new AtomicBoolean(false);

        ExecutionLifecycle lifecycle = new ExecutionLifecycle() {
            @Override
            public void beforeExecute(
                    Execution execution,
                    ExecutionContext context
            ) {

                beforeCalled.set(true);

            }

            @Override
            public void afterExecute(
                    Execution execution,
                    ExecutionContext context
            ) {

                afterCalled.set(true);

            }

        };

        Execution execution = new Execution("execution-001", DEFINITION);

        ExecutionEngine engine = TestExecutionEngineFactory.create(lifecycle);

        engine.execute(execution);

        assertTrue(beforeCalled.get());

        assertTrue(afterCalled.get());
        assertEquals(
                ExecutionStatus.SUCCEEDED,
                execution.status());
    }

    @Test
    void shouldInvokeErrorLifecycle() {

        AtomicBoolean errorCalled = new AtomicBoolean(false);

        ExecutionLifecycle lifecycle = new ExecutionLifecycle() {

            @Override
            public void onError(
                    Execution execution,
                    ExecutionContext context,
                    Exception exception
            ) {

                errorCalled.set(true);

            }
        };

        Execution execution = new Execution("execution-001", DEFINITION);


        ExecutionEngine engine =
                TestExecutionEngineFactory.create(
                        lifecycle,
                        request -> {
                            throw new RuntimeException("failed");
                        });


        try {
            engine.execute(execution);

        } catch (RuntimeException ignored) {

        }

        assertTrue(errorCalled.get());
    }
}
