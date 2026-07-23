package io.agentflow.execution.environment;

import io.agentflow.execution.ExecutionContextFactory;
import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.lifecycle.ExecutionLifecycle;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;

import java.util.List;
import java.util.Objects;

public class DefaultExecutionEnvironment implements ExecutionEnvironment {

    private final ExecutionContextFactory contextFactory;


    private final ExecutionLifecycle lifecycle;


    private final List<ExecutionInterceptor> interceptors;


    private final ExecutionPipeline pipeline;


    private final ExecutionResultHandler resultHandler;

    public DefaultExecutionEnvironment(
            ExecutionContextFactory contextFactory,
            ExecutionLifecycle lifecycle,
            List<ExecutionInterceptor> interceptors,
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler
    ) {

        this.contextFactory =
                Objects.requireNonNull(
                        contextFactory,
                        "contextFactory must not be null"
                );


        this.lifecycle =
                Objects.requireNonNull(
                        lifecycle,
                        "lifecycle must not be null"
                );


        this.interceptors =
                List.copyOf(
                        Objects.requireNonNull(
                                interceptors,
                                "interceptors must not be null"
                        )
                );


        this.pipeline =
                Objects.requireNonNull(
                        pipeline,
                        "pipeline must not be null"
                );


        this.resultHandler =
                Objects.requireNonNull(
                        resultHandler,
                        "resultHandler must not be null"
                );

    }
    @Override
    public ExecutionContextFactory contextFactory() {
        return contextFactory;
    }

    @Override
    public ExecutionLifecycle lifecycle() {
        return lifecycle;
    }

    @Override
    public List<ExecutionInterceptor> interceptors() {
        return interceptors;
    }

    @Override
    public ExecutionPipeline pipeline() {
        return pipeline;
    }

    @Override
    public ExecutionResultHandler resultHandler() {
        return resultHandler;
    }
}
