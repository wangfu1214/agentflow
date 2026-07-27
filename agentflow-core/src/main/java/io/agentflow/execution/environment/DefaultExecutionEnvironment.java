package io.agentflow.execution.environment;

import io.agentflow.execution.ExecutionContextFactory;
import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.ExecutionLifecycle;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;

import java.util.List;
import java.util.Objects;

public class DefaultExecutionEnvironment implements ExecutionEnvironment {

    private final ExecutionContextFactory contextFactory;


    private final ExecutionLifecycle lifecycle;


    private final ExecutionInterceptorChain interceptorChain;


    private final ExecutionPipeline pipeline;


    private final ExecutionResultHandler resultHandler;

    public DefaultExecutionEnvironment(
            ExecutionContextFactory contextFactory,
            ExecutionLifecycle lifecycle,
            ExecutionInterceptorChain interceptorChain,
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

        this.interceptorChain =
                        Objects.requireNonNull(
                                interceptorChain,
                                "interceptorChain must not be null"
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
    public ExecutionPipeline pipeline() {
        return pipeline;
    }

    @Override
    public ExecutionResultHandler resultHandler() {
        return resultHandler;
    }

    @Override
    public ExecutionInterceptorChain interceptorChain() {
        return interceptorChain;
    }
}
