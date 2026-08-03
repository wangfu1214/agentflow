package io.agentflow.execution;


import io.agentflow.execution.environment.DefaultExecutionEnvironment;
import io.agentflow.execution.environment.ExecutionEnvironment;
import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.NoopExecutionLifecycle;
import io.agentflow.execution.pipeline.*;
import io.agentflow.execution.result.DefaultExecutionResultHandler;
import io.agentflow.model.ModelResponse;
import io.agentflow.tool.ToolDefinition;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DefaultExecutionInterceptorTest {


    private static final ExecutionDefinition DEFINITION =
            new ExecutionDefinition(
                    "assistant",
                    "You are helpful.",
                    "Hello",
                    List.of());

    @Test
    void shouldInvokeInterceptorBeforeAndAfterExecution() {

        AtomicBoolean beforeCalled =
                new AtomicBoolean(false);

        AtomicBoolean afterCalled =
                new AtomicBoolean(false);

        ExecutionInterceptor interceptor =
                new ExecutionInterceptor() {
                    @Override
                    public void before(
                            Execution execution,
                            ExecutionContext context) {
                        beforeCalled.set(true);
                    }

                    @Override
                    public void after(
                            Execution execution,
                            ExecutionContext context
                    ) {

                        afterCalled.set(true);

                    }

                };


        Execution execution = new Execution("execution-001", DEFINITION);

        ExecutionEngine engine = createEngine(interceptor);

        ExecutionResult result = engine.execute(execution);

        assertEquals(
                "success",
                result.content());

        assertTrue(beforeCalled.get());

        assertTrue(afterCalled.get());

        assertEquals(
                ExecutionStatus.SUCCEEDED,
                execution.status());
    }


    @Test
    void shouldInvokeInterceptorOnError() {


        AtomicBoolean errorCalled =
                new AtomicBoolean(false);


        ExecutionInterceptor interceptor =
                new ExecutionInterceptor() {


                    @Override
                    public void onError(
                            Execution execution,
                            ExecutionContext context,
                            Exception exception
                    ) {

                        errorCalled.set(true);

                    }

                };


        Execution execution =
                new Execution(
                        "execution-001",
                        DEFINITION
                );


        ExecutionEngine engine =
                createFailedEngine(
                        interceptor
                );


        try {

            engine.execute(execution);

        } catch (RuntimeException ignored) {

        }


        assertTrue(
                errorCalled.get()
        );


        assertEquals(
                ExecutionStatus.FAILED,
                execution.status()
        );

    }


    private ExecutionEngine createEngine(
            ExecutionInterceptor interceptor
    ) {


        ExecutionPipeline pipeline =
                new DefaultExecutionPipeline(
                        List.of(
                                new ModelExecutionStep(
                                        request ->
                                                ModelResponse.of(
                                                        "success"
                                                )
                                )
                        )
                );

        DefaultExecutionEnvironment environment = new DefaultExecutionEnvironment(
                new DefaultExecutionContextFactory(),
                new NoopExecutionLifecycle(),
                new ExecutionInterceptorChain(List.of(interceptor)),
                pipeline,
                new DefaultExecutionResultHandler()
        );
        return new DefaultExecutionEngine(environment);
    }


    private ExecutionEngine createFailedEngine(
            ExecutionInterceptor interceptor
    ) {


        ExecutionPipeline pipeline =
                new DefaultExecutionPipeline(
                        List.of(
                                context -> {
                                    throw new RuntimeException(
                                            "pipeline failed"
                                    );
                                }
                        )
                );
        ExecutionEnvironment environment =
                new DefaultExecutionEnvironment(
                        new DefaultExecutionContextFactory(),
                        new NoopExecutionLifecycle(),
                        new ExecutionInterceptorChain(List.of(interceptor)),
                        pipeline,
                        new DefaultExecutionResultHandler()
                );

        return new DefaultExecutionEngine(environment);
    }

}