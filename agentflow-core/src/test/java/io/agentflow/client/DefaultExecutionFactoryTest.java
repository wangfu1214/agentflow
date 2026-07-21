package io.agentflow.client;

import io.agentflow.agent.Agent;
import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultExecutionFactoryTest {

    @Test
    void shouldCreateExecutionFromAgentAndRequest() {
        Agent agent = Agent.builder()
                .name("assistant")
                .systemPrompt("You are helpful.")
                .build();

        AgentRequest request =
                AgentRequest.of("Hello");

        DefaultExecutionFactory factory =
                new DefaultExecutionFactory(
                        () -> "execution-001"
                );

        Execution execution =
                factory.create(agent, request);

        assertEquals(
                "execution-001",
                execution.id()
        );
        assertEquals(
                ExecutionStatus.CREATED,
                execution.status()
        );
        assertEquals(
                "assistant",
                execution.definition().agentName()
        );
        assertEquals(
                "You are helpful.",
                execution.definition().systemPrompt()
        );
        assertEquals(
                "Hello",
                execution.definition().input()
        );
    }
}
