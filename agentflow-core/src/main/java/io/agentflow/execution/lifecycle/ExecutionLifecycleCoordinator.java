package io.agentflow.execution.lifecycle;


import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionResult;
import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.result.ExecutionResultHandler;
import io.agentflow.execution.pipeline.ExecutionPipeline;


import java.util.List;
import java.util.Objects;


/**
 * Coordinates the lifecycle of one execution.
 */
public final class ExecutionLifecycleCoordinator {


    private final ExecutionLifecycle lifecycle;


    private final ExecutionInterceptorChain interceptorChain;


    private final ExecutionPipeline pipeline;


    private final ExecutionResultHandler resultHandler;



    public ExecutionLifecycleCoordinator(
            ExecutionLifecycle lifecycle,
            ExecutionInterceptorChain interceptorChain,
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler
    ) {

        this.lifecycle =
                Objects.requireNonNull(
                        lifecycle,
                        "lifecycle must not be null"
                );


        this.interceptorChain =
                Objects.requireNonNull(interceptorChain,
                        "interceptorChain must not be null");


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



    public ExecutionResult execute(
            Execution execution,
            ExecutionContext context
    ) {

        execution.start();


        context.record()
                .start();


        try {


            lifecycle.beforeExecute(
                    execution,
                    context
            );


            interceptorChain.before(execution, context);

            pipeline.execute(context);

            interceptorChain.after(execution, context);

            ExecutionResult result =
                    resultHandler.handle(
                            context
                    );

            lifecycle.afterExecute(
                    execution,
                    context
            );

            context.record()
                    .complete();

            execution.succeed();

            return result;
        } catch(RuntimeException exception) {

            interceptorChain.onError(execution, context, exception);

            lifecycle.onError(
                    execution,
                    context,
                    exception
            );

            context.record()
                    .fail();

            execution.fail();

            throw exception;

        }

    }

}