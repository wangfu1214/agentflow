package io.agentflow.execution.interceptor;

import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;

import java.util.List;
import java.util.Objects;


/**
 * Executes execution interceptors in order.
 */
public final class ExecutionInterceptorChain {


    private final List<ExecutionInterceptor> interceptors;


    public ExecutionInterceptorChain(
            List<ExecutionInterceptor> interceptors
    ) {

        this.interceptors =
                List.copyOf(
                        Objects.requireNonNull(
                                interceptors,
                                "interceptors must not be null"
                        )
                );

    }


    public void before(
            Execution execution,
            ExecutionContext context
    ) {

        for(
                ExecutionInterceptor interceptor :
                interceptors
        ) {

            interceptor.before(
                    execution,
                    context
            );

        }

    }


    public void after(
            Execution execution,
            ExecutionContext context
    ) {

        for(
                ExecutionInterceptor interceptor :
                interceptors
        ) {

            interceptor.after(
                    execution,
                    context
            );

        }

    }


    public void onError(
            Execution execution,
            ExecutionContext context,
            Exception exception
    ) {
        for(ExecutionInterceptor interceptor : interceptors) {
            interceptor.onError(
                    execution,
                    context,
                    exception
            );
        }

    }

}