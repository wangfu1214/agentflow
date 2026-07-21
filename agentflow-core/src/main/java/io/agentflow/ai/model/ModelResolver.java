package io.agentflow.ai.model;

import io.agentflow.ai.agent.Agent;
import io.agentflow.ai.agent.AgentRequest;
import org.springframework.stereotype.Component;

@Component
public class ModelResolver {
    public String resolveModel(Agent agent, AgentRequest request) {
        if (request.getModel() != null && !request.getModel().isBlank()) {
            return request.getModel();
        }

        if (agent.getModelOptions() == null) {
            return null;
        }

        return agent.getModelOptions().getModel();
    }

    public ModelOptions resolveOptions(Agent agent, AgentRequest request) {
        if (agent.getModelOptions() == null) {
            return ModelOptions.builder()
                    .model(resolveModel(agent, request))
                    .build();
        }

        return agent.getModelOptions().toBuilder()
                .model(resolveModel(agent, request))
                .build();
    }
}
