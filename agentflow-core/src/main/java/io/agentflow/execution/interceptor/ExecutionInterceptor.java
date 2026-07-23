package io.agentflow.execution.interceptor;

import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;

public interface ExecutionInterceptor {

    default void before(
            Execution execution,
            ExecutionContext context
    ){

    }


    default void after(
            Execution execution,
            ExecutionContext context
    ){

    }


    default void onError(
            Execution execution,
            ExecutionContext context,
            Exception exception
    ){

    }
}
