package io.agentflow.execution.environment;

import io.agentflow.execution.ExecutionContextFactory;
import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.ExecutionLifecycle;
import io.agentflow.execution.lifecycle.NoopExecutionLifecycle;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;

import java.util.List;

public class DefaultExecutionEnvironmentBuilder implements ExecutionEnvironmentBuilder {


    private ExecutionContextFactory contextFactory;


    private ExecutionLifecycle lifecycle =
            new NoopExecutionLifecycle();


    private ExecutionInterceptorChain interceptorChain =
            new ExecutionInterceptorChain(List.of());


    private ExecutionPipeline pipeline;


    private ExecutionResultHandler resultHandler;

    @Override
    public ExecutionEnvironment build() {
        return new DefaultExecutionEnvironment(
                contextFactory,
                lifecycle,
                interceptorChain,
                pipeline,
                resultHandler
        );
    }

    @Override
    public ExecutionEnvironmentBuilder contextFactory(ExecutionContextFactory contextFactory) {
        this.contextFactory = contextFactory;
        return this;
    }

    @Override
    public ExecutionEnvironmentBuilder lifecycle(ExecutionLifecycle lifecycle) {
        this.lifecycle = lifecycle;
        return this;
    }

    @Override
    public ExecutionEnvironmentBuilder interceptors(List<ExecutionInterceptor> interceptors) {
        this.interceptorChain =     new ExecutionInterceptorChain(interceptors);
        return this;
    }

    @Override
    public ExecutionEnvironmentBuilder pipeline(ExecutionPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    @Override
    public ExecutionEnvironmentBuilder resultHandler(ExecutionResultHandler resultHandler) {
        this.resultHandler = resultHandler;
        return this;
    }
}
