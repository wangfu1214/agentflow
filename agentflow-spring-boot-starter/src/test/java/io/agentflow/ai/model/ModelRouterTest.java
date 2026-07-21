package io.agentflow.ai.model;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelRouterTest {

    void shouldRouteToSpringAIProvider() {
        ModelProviderRegistry registry = new ModelProviderRegistry();

        ModelInvoker modelInvoker = (agent, request, systemPrompt) -> null;

        ModelProvider modelProvider = new ModelProvider() {
            @Override
            public String name() {
                return "spring-ai";
            }

            @Override
            public ModelInvoker createInvoker() {
                return modelInvoker;
            }
        };
        registry.register(modelProvider);

        ModelRouter router = new ModelRouter(registry);
        assertThat(router.route("spring-ai"))
                .isSameAs(modelInvoker);
    }
}
