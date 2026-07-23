package io.agentflow.execution.environment;

import io.agentflow.execution.ExecutionContextFactory;
import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.lifecycle.ExecutionLifecycle;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;

import java.util.List;

public interface ExecutionEnvironment {

    ExecutionContextFactory contextFactory();


    ExecutionLifecycle lifecycle();


    List<ExecutionInterceptor> interceptors();


    ExecutionPipeline pipeline();


    ExecutionResultHandler resultHandler();
}
