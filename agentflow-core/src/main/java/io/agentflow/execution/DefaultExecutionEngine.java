package io.agentflow.execution;

import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.lifecycle.ExecutionLifecycle;
import io.agentflow.execution.lifecycle.NoopExecutionLifecycle;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;

import java.util.List;
import java.util.Objects;

/**
 * Default execution engine that invokes one model and drives
 * the basic execution lifecycle.
 */
public class DefaultExecutionEngine implements ExecutionEngine {

    private final ExecutionPipeline pipeline;

    private final ExecutionResultHandler resultHandler;

    private final ExecutionContextFactory contextFactory;

    private final ExecutionLifecycle lifecycle;

    private final List<ExecutionInterceptor> interceptors;

    public DefaultExecutionEngine(
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler
    ) {
        this(pipeline, resultHandler, new DefaultExecutionContextFactory());
    }

    public DefaultExecutionEngine(
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler,
            ExecutionContextFactory contextFactory
    ) {
        this(pipeline, resultHandler, contextFactory, new NoopExecutionLifecycle());
    }

    public DefaultExecutionEngine(
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler,
            ExecutionContextFactory contextFactory,
            ExecutionLifecycle lifecycle
    ) {
        this(pipeline, resultHandler, contextFactory, lifecycle, List.of());
    }

    public DefaultExecutionEngine(
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler,
            ExecutionContextFactory contextFactory,
            ExecutionLifecycle lifecycle,
            List<ExecutionInterceptor> interceptors
    ) {
        this.pipeline = Objects.requireNonNull(
                pipeline,
                "pipeline must not be null"
        );

        this.resultHandler =
                Objects.requireNonNull(
                        resultHandler,
                        "resultHandler must not be null"
                );

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
        this.interceptors = interceptors;
    }

    @Override
    public ExecutionResult execute(
            Execution execution
    ) {
        Objects.requireNonNull(
                execution,
                "execution must not be null"
        );

        ExecutionContext context = contextFactory.create(execution);

        execution.start();

        try {

            lifecycle.beforeExecute(execution, context);

            for (ExecutionInterceptor interceptor : interceptors) {
                interceptor.before(execution, context);
            }

            pipeline.execute(context);

            for (ExecutionInterceptor interceptor : interceptors) {
                interceptor.after(execution, context);
            }

            ExecutionResult result = resultHandler.handler(context);

            lifecycle.afterExecute(execution, context);

            execution.succeed();

            return result;
        } catch (RuntimeException exception) {
            for(ExecutionInterceptor interceptor: interceptors){

                interceptor.onError(
                        execution,
                        context,
                        exception);

            }
            lifecycle.onError(execution, context, exception);
            execution.fail();
            throw exception;
        }
    }
}
