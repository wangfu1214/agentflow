package io.agentflow.client;


import io.agentflow.agent.Agent;
import io.agentflow.agent.AgentRegistry;
import io.agentflow.agent.DefaultAgentExecutionFactory;
import io.agentflow.agent.DefaultAgentRegistry;
import io.agentflow.execution.DefaultExecutionContextFactory;
import io.agentflow.execution.DefaultExecutionEngine;
import io.agentflow.execution.ExecutionEngine;
import io.agentflow.execution.environment.DefaultExecutionEnvironment;
import io.agentflow.execution.environment.ExecutionEnvironment;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.NoopExecutionLifecycle;
import io.agentflow.execution.pipeline.DefaultExecutionPipeline;
import io.agentflow.execution.pipeline.ModelExecutionStep;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.DefaultExecutionResultHandler;
import io.agentflow.model.ModelInvoker;
import io.agentflow.model.ModelResponse;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DefaultAgentFlowTest {

    @Test
    void shouldExecuteAgentThroughFlow() {
        Agent agent =
                Agent.builder()
                        .name("assistant")
                        .systemPrompt("You are helpful.")
                        .build();


        AgentRegistry registry = new DefaultAgentRegistry();
        registry.register(agent);

        ModelInvoker modelInvoker =
                request -> {

                    assertEquals(
                            "You are helpful.",
                            request.systemPrompt());
                    assertEquals(
                            "Hello",
                            request.input());
                    return ModelResponse.of("Hello from model");
                };

        ExecutionPipeline pipeline =
                new DefaultExecutionPipeline(
                        List.of(new ModelExecutionStep(modelInvoker)));

        ExecutionEnvironment environment =
                new DefaultExecutionEnvironment(
                        new DefaultExecutionContextFactory(),
                        new NoopExecutionLifecycle(),
                        new ExecutionInterceptorChain(
                                List.of()
                        ),
                        pipeline,
                        new DefaultExecutionResultHandler());

        ExecutionEngine engine =
                new DefaultExecutionEngine(environment);

        DefaultExecutionFactory executionFactory =
                new DefaultExecutionFactory(
                        () -> "execution-001");

        DefaultAgentExecutionFactory factory =
                new DefaultAgentExecutionFactory(executionFactory);

        AgentFlow agentFlow =
                new DefaultAgentFlow(
                        registry,
                        factory,
                        engine
                );

        AgentResponse response =
                agentFlow.execute("assistant", AgentRequest.of("Hello"));

        assertEquals("execution-001", response.executionId());
        assertEquals("Hello from model", response.content());
    }

}