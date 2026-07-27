package io.agentflow.execution.environment;

import io.agentflow.execution.ExecutionContextFactory;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.ExecutionLifecycle;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;


public interface ExecutionEnvironment {

    ExecutionContextFactory contextFactory();

    ExecutionLifecycle lifecycle();

    ExecutionPipeline pipeline();

    ExecutionResultHandler resultHandler();

    ExecutionInterceptorChain interceptorChain();
}
