package io.agentflow.execution;

import io.agentflow.execution.environment.ExecutionEnvironment;
import io.agentflow.execution.interceptor.ExecutionInterceptor;

import java.util.Objects;

/**
 * Default execution engine that invokes one model and drives
 * the basic execution lifecycle.
 */
public class DefaultExecutionEngine implements ExecutionEngine {

    private final ExecutionEnvironment environment;

    public DefaultExecutionEngine(ExecutionEnvironment environment) {
        this.environment =
                Objects.requireNonNull(
                        environment,
                        "environment must not be null"
                );
    }

    @Override
    public ExecutionResult execute(
            Execution execution
    ) {
        Objects.requireNonNull(
                execution,
                "execution must not be null"
        );

        ExecutionContext context = environment.contextFactory().create(execution);

        execution.start();

        try {

            environment.lifecycle().beforeExecute(execution, context);

            for (ExecutionInterceptor interceptor : environment.interceptors()) {
                interceptor.before(execution, context);
            }

            environment.pipeline().execute(context);

            for (ExecutionInterceptor interceptor : environment.interceptors()) {
                interceptor.after(execution, context);
            }

            ExecutionResult result = environment.resultHandler().handle(context);

            environment.lifecycle().afterExecute(execution, context);

            execution.succeed();

            return result;
        } catch (RuntimeException exception) {
            for(ExecutionInterceptor interceptor: environment.interceptors()){

                interceptor.onError(
                        execution,
                        context,
                        exception);

            }
            environment.lifecycle().onError(execution, context, exception);
            execution.fail();
            throw exception;
        }
    }
}
