package io.agentflow.execution;

import io.agentflow.execution.environment.DefaultExecutionEnvironment;
import io.agentflow.execution.environment.ExecutionEnvironment;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.ExecutionLifecycle;
import io.agentflow.execution.pipeline.DefaultExecutionPipeline;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.pipeline.ExecutionStep;
import io.agentflow.execution.pipeline.ModelExecutionStep;
import io.agentflow.execution.result.DefaultExecutionResultHandler;
import io.agentflow.model.ModelInvoker;
import io.agentflow.model.ModelResponse;

import java.util.List;

public class TestExecutionEngineFactory {

    private TestExecutionEngineFactory() {

    }


    public static ExecutionEngine create(
            ExecutionLifecycle lifecycle
    ) {

        ModelInvoker modelInvoker =
                request ->
                        ModelResponse.of(
                                "success"
                        );


        return create(
                lifecycle,
                new ModelExecutionStep(
                        modelInvoker
                )
        );
    }



    public static ExecutionEngine create(
            ExecutionLifecycle lifecycle,
            ModelInvoker modelInvoker
    ) {


        return create(
                lifecycle,
                new ModelExecutionStep(
                        modelInvoker
                )
        );
    }



    private static ExecutionEngine create(
            ExecutionLifecycle lifecycle,
            ExecutionStep step
    ) {


        ExecutionPipeline pipeline =
                new DefaultExecutionPipeline(
                        List.of(step)
                );

        ExecutionEnvironment environment =
                new DefaultExecutionEnvironment(
                        new DefaultExecutionContextFactory(),
                        lifecycle,
                        new ExecutionInterceptorChain(List.of()),
                        pipeline,
                        new DefaultExecutionResultHandler()
                );
        return new DefaultExecutionEngine(environment);
    }
}
