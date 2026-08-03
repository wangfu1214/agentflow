package io.agentflow.execution;


import io.agentflow.execution.environment.DefaultExecutionEnvironment;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.NoopExecutionLifecycle;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultExecutionEnvironmentTest {

    @Test
    void shouldExposeExecutionComponents() {

        ExecutionContextFactory contextFactory =
                new DefaultExecutionContextFactory();

        ExecutionPipeline pipeline =
                context ->
                        context.put("test", "value");

        ExecutionResultHandler resultHandler =
                context -> ExecutionResult.of("result");

        DefaultExecutionEnvironment environment = new DefaultExecutionEnvironment(
                        contextFactory,
                        new NoopExecutionLifecycle(),
                        new ExecutionInterceptorChain(List.of()),
                        pipeline,
                        resultHandler);

        assertEquals(contextFactory, environment.contextFactory());
        assertEquals(pipeline, environment.pipeline());
        assertEquals(resultHandler, environment.resultHandler());
    }

}