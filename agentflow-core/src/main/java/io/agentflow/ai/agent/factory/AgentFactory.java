package io.agentflow.ai.agent.factory;

import io.agentflow.ai.agent.Agent;
import io.agentflow.ai.model.ModelOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class AgentFactory {

    public Agent create(io.agentflow.ai.agent.annotation.Agent agent) {
        return Agent.builder()
                .modelOptions(ModelOptions.builder()
                        .provider(agent.provider())
                        .model(agent.model())
                        .build())
                .name(agent.name())
                .description(agent.description())
                .systemPromptName(agent.systemPrompt())
                .toolNames(List.of(agent.tools()))
                .enabled(agent.enabled())
                .build();
    }
}
