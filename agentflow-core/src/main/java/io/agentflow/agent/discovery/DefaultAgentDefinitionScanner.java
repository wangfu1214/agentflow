package io.agentflow.agent.discovery;

import io.agentflow.agent.Agent;
import io.agentflow.agent.annotation.AgentDefinition;

import java.util.*;

public class DefaultAgentDefinitionScanner implements AgentDefinitionScanner {

    @Override
    public Collection<Agent> scan(Collection<Class<?>> classes) {
        Objects.requireNonNull(classes, "classes can not be null");

        List<Agent> agents = new ArrayList<>();

        for (Class<?> clazz : classes) {
            AgentDefinition annotation = clazz.getAnnotation(AgentDefinition.class);
            if (annotation == null) {
                continue;
            }
            Agent agent = Agent.builder()
                    .name(annotation.name())
                    .systemPrompt(annotation.systemPrompt())
                    .requiredTools(Arrays.asList(annotation.tools()))
                    .build();
            agents.add(agent);
        }
        return List.copyOf(agents);
    }
}
