package io.agentflow.ai.model.spring;

import io.agentflow.ai.agent.Agent;
import io.agentflow.ai.agent.AgentRequest;
import io.agentflow.ai.agent.AgentResponse;
import io.agentflow.ai.model.ModelInvoker;
import io.agentflow.ai.model.ModelResolver;
import io.agentflow.ai.tool.registry.ToolRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringAIModelInvoker implements ModelInvoker {

    private final ChatClientProvider chatClientProvider;

    private final ToolRegistry toolRegistry;

    private final ModelResolver modelResolver;

    @Override
    public AgentResponse invoke(Agent agent, AgentRequest request, String systemPrompt) {
        String model = modelResolver.resolveModel(agent, request);
        ChatClient chatClient = chatClientProvider.get(model);
        String answer = chatClient
                .prompt()
                .system(systemPrompt)
                .user(request.getUserMessage())
                .tools(toolRegistry.getAllTools())
                .call()
                .content();

        return AgentResponse.builder()
                .answer(answer)
                .model(model)
                .success(true)
                .build();
    }
}
