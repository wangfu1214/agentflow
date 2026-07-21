package io.agentflow.autoconfigure;

import io.agentflow.ai.model.ModelResolver;
import io.agentflow.ai.prompt.PromptRenderer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class AgentFlowAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withBean(ChatClient.class, () -> Mockito.mock(ChatClient.class))
                    .withConfiguration(
                            AutoConfigurations.of(
                                    AgentFlowAutoConfiguration.class
                            )
                    );

    @Test
    void shouldLoadAgentFlowConfiguration() {

        contextRunner.run(context -> {

            assertThat(context)
                    .hasSingleBean(AgentFlowProperties.class);

            assertThat(context)
                    .hasSingleBean(PromptRenderer.class);

            assertThat(context)
                    .hasSingleBean(ModelResolver.class);
        });
    }
}
