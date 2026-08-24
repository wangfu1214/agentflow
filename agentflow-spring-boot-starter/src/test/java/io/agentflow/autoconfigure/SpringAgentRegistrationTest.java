package io.agentflow.autoconfigure;

import io.agentflow.agent.Agent;
import io.agentflow.agent.AgentRegistry;
import io.agentflow.agent.annotation.AgentDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class SpringAgentRegistrationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(AutoConfigurations.of(AgentFlowAutoConfiguration.class));

    @Test
    void shouldRegisterAnnotatedAgentAutomatically() {
        contextRunner
                .withUserConfiguration(TestConfiguration.class)
                .run(context -> {
                    assertThat(context).hasNotFailed();

                    AgentRegistry registry =
                            context.getBean(AgentRegistry.class);

                    Agent agent =
                            registry.get("assistant");

                    assertThat(agent.name())
                            .isEqualTo("assistant");

                    assertThat(agent.systemPrompt())
                            .isEqualTo("You are helpful");

                    assertThat(agent.requiredTools())
                            .containsExactly("order-query");
                });
    }

    @Test
    void shouldStartWhenNoAgentDefinitionsExist() {
        contextRunner.run(context -> {
            assertThat(context).hasNotFailed();

            AgentRegistry registry =
                    context.getBean(AgentRegistry.class);

            assertThat(registry.list())
                    .isEmpty();
        });
    }

    @Configuration(proxyBeanMethods = false)
    static class TestConfiguration {

        @Bean
        AssistantAgent assistantAgent() {
            return new AssistantAgent();
        }

        @AgentDefinition(
                name = "assistant",
                systemPrompt = "You are helpful",
                tools = {"order-query"}
        )
        static class AssistantAgent {
        }
    }
}