package io.agentflow.execution;

import io.agentflow.execution.environment.DefaultExecutionEnvironmentBuilder;
import io.agentflow.execution.environment.ExecutionEnvironment;
import io.agentflow.execution.lifecycle.NoopExecutionLifecycle;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultExecutionEnvironmentBuilderTest {

    @Test
    void shouldBuildExecutionEnvironment() {
        ExecutionContextFactory factory = new DefaultExecutionContextFactory();
        ExecutionPipeline pipeline = context -> {
        };
        ExecutionResultHandler handler = context -> ExecutionResult.of("result");
        ExecutionEnvironment environment = new DefaultExecutionEnvironmentBuilder()
                .contextFactory(factory)
                .lifecycle(new NoopExecutionLifecycle())
                .interceptors(List.of())
                .pipeline(pipeline)
                .resultHandler(handler)
                .build();
        assertEquals(factory, environment.contextFactory());
        assertEquals(pipeline, environment.pipeline());
        assertEquals(handler, environment.resultHandler());
    }
}