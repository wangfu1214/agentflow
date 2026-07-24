package io.agentflow.execution.lifecycle;


import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionResult;
import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.result.ExecutionResultHandler;
import io.agentflow.execution.pipeline.ExecutionPipeline;


import java.util.List;
import java.util.Objects;


/**
 * Coordinates the lifecycle of one execution.
 */
public final class ExecutionLifecycleCoordinator {


    private final ExecutionLifecycle lifecycle;


    private final List<ExecutionInterceptor> interceptors;


    private final ExecutionPipeline pipeline;


    private final ExecutionResultHandler resultHandler;



    public ExecutionLifecycleCoordinator(
            ExecutionLifecycle lifecycle,
            List<ExecutionInterceptor> interceptors,
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler
    ) {

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


            for(
                    ExecutionInterceptor interceptor :
                    interceptors
            ) {

                interceptor.before(
                        execution,
                        context
                );

            }


            pipeline.execute(context);



            for(
                    ExecutionInterceptor interceptor :
                    interceptors
            ) {

                interceptor.after(
                        execution,
                        context
                );

            }



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


            for(
                    ExecutionInterceptor interceptor :
                    interceptors
            ) {

                interceptor.onError(
                        execution,
                        context,
                        exception
                );

            }


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