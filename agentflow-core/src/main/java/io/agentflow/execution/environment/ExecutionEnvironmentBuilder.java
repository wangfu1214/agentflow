package io.agentflow.execution.environment;


import io.agentflow.execution.ExecutionContextFactory;
import io.agentflow.execution.interceptor.ExecutionInterceptor;
import io.agentflow.execution.lifecycle.ExecutionLifecycle;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;

import java.util.List;


public interface ExecutionEnvironmentBuilder {


    ExecutionEnvironment build();


    ExecutionEnvironmentBuilder contextFactory(
            ExecutionContextFactory contextFactory
    );


    ExecutionEnvironmentBuilder lifecycle(
            ExecutionLifecycle lifecycle
    );


    ExecutionEnvironmentBuilder interceptors(
            List<ExecutionInterceptor> interceptors
    );


    ExecutionEnvironmentBuilder pipeline(
            ExecutionPipeline pipeline
    );


    ExecutionEnvironmentBuilder resultHandler(
            ExecutionResultHandler resultHandler
    );

}