package io.agentflow.execution;

import io.agentflow.execution.pipeline.DefaultExecutionPipeline;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.pipeline.ModelExecutionStep;
import io.agentflow.execution.result.DefaultExecutionResultHandler;
import io.agentflow.model.ModelInvoker;
import io.agentflow.model.ModelResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultExecutionEngineTest {

    private static final ExecutionDefinition DEFINITION =
            new ExecutionDefinition(
                    "assistant",
                    "You are helpful.",
                    "Hello"
            );

    @Test
    void shouldExecuteModelAndCompleteExecution() {
        ModelInvoker modelInvoker = request -> {
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
        };

        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );

        ExecutionEngine engine = createEngine(modelInvoker);

        ExecutionResult result =
                engine.execute(execution);

        assertEquals(
                "Hello from model",
                result.content()
        );
        assertEquals(
                ExecutionStatus.SUCCEEDED,
                execution.status()
        );
    }

    @Test
    void shouldMarkExecutionFailedWhenModelFails() {
        RuntimeException modelFailure =
                new RuntimeException("model unavailable");

        ModelInvoker modelInvoker = request -> {
            throw modelFailure;
        };

        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );

        ExecutionEngine engine = createEngine(modelInvoker);

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> engine.execute(execution)
        );

        assertEquals(modelFailure, thrown);
        assertEquals(
                ExecutionStatus.FAILED,
                execution.status()
        );
    }

    @Test
    void shouldRejectNullModelResponse() {
        ModelInvoker modelInvoker =
                request -> null;

        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );

        ExecutionEngine engine = createEngine(modelInvoker);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> engine.execute(execution)
        );

        assertEquals(
                "modelInvoker returned null response",
                exception.getMessage()
        );
        assertEquals(
                ExecutionStatus.FAILED,
                execution.status()
        );
    }

    @Test
    void shouldRejectExecutingCompletedExecutionAgain() {
        ModelInvoker modelInvoker =
                request -> ModelResponse.of("done");

        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );

        ExecutionEngine engine = createEngine(modelInvoker);

        engine.execute(execution);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> engine.execute(execution)
        );

        assertEquals(
                "expected execution status CREATED but was SUCCEEDED",
                exception.getMessage()
        );
        assertEquals(
                ExecutionStatus.SUCCEEDED,
                execution.status()
        );
    }

    private ExecutionEngine createEngine(
            ModelInvoker modelInvoker
    ) {

        ExecutionPipeline pipeline =
                new DefaultExecutionPipeline(
                        List.of(
                                new ModelExecutionStep(
                                        modelInvoker
                                )
                        )
                );

        return new DefaultExecutionEngine(
                pipeline,
                new DefaultExecutionResultHandler()
        );
    }
}
