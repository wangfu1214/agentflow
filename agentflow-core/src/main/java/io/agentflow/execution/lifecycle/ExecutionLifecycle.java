package io.agentflow.execution.lifecycle;

import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionContext;

public interface ExecutionLifecycle {

    default void beforeExecute(Execution execution, ExecutionContext context) {

    }


    default void afterExecute(Execution execution, ExecutionContext context) {

    }


    default void onError(Execution execution, ExecutionContext context, Exception exception) {

    }
}
