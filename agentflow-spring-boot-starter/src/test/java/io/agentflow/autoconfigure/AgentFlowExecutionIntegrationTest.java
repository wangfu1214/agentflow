package io.agentflow.autoconfigure;

import io.agentflow.agent.annotation.AgentDefinition;
import io.agentflow.client.AgentFlow;
import io.agentflow.client.AgentRequest;
import io.agentflow.client.AgentResponse;
import io.agentflow.model.ModelInvoker;
import io.agentflow.model.ModelResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class AgentFlowExecutionIntegrationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(AutoConfigurations.of(AgentFlowAutoConfiguration.class))
                    .withUserConfiguration(TestConfiguration.class);

    @Test
    void shouldExecuteAnnotatedAgent() {
        contextRunner.run(context -> {
            assertThat(context).hasNotFailed();

            AgentFlow agentFlow =
                    context.getBean(AgentFlow.class);

            AgentResponse response = agentFlow.execute(
                    "assistant",
                    AgentRequest.of("hello"));

            assertThat(response.content())
                    .isEqualTo("You are a helpful assistant|hello");

            assertThat(response.executionId())
                    .isNotBlank();
        });
    }

    @Configuration(proxyBeanMethods = false)
    static class TestConfiguration {

        @Bean
        AssistantAgent assistantAgent() {
            return new AssistantAgent();
        }

        @Bean
        ModelInvoker modelInvoker() {
            return request ->
                    ModelResponse.of(
                            request.systemPrompt()
                                    + "|"
                                    + request.input());
        }

        @AgentDefinition(
                name = "assistant",
                systemPrompt = "You are a helpful assistant"
        )
        static class AssistantAgent {
        }
    }
}