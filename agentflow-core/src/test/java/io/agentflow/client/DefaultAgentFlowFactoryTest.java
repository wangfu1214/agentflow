package io.agentflow.client;


import io.agentflow.agent.Agent;
import io.agentflow.agent.AgentExecutionFactory;
import io.agentflow.agent.AgentRegistry;
import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionEngine;
import io.agentflow.execution.ExecutionResult;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultAgentFlowFactoryTest {


    @Test
    void shouldCreateAgentFlow() {

        AgentRegistry registry =
                new AgentRegistry() {

                    @Override
                    public void register(Agent agent) {

                    }

                    public Agent get(String id){
                        return null;
                    }

                    public Collection<Agent> list(){
                        return List.of();
                    }

                };

        AgentExecutionFactory factory = (agent, request) -> null;

        ExecutionEngine engine =
                execution ->
                        ExecutionResult.of("ok");

        AgentFlowFactory flowFactory =
                new DefaultAgentFlowFactory(
                        registry,
                        factory,
                        engine);

        AgentFlow flow =
                flowFactory.create();

        assertNotNull(flow);
    }

}