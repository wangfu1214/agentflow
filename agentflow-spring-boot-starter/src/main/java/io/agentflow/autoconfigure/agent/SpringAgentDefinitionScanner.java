package io.agentflow.autoconfigure.agent;

import io.agentflow.agent.Agent;
import io.agentflow.agent.AgentRegistry;
import io.agentflow.agent.annotation.AgentDefinition;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

public class SpringAgentDefinitionScanner {

    private final ApplicationContext applicationContext;

    public SpringAgentDefinitionScanner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<Agent> scan() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(AgentDefinition.class);
        return beans.values()
                .stream()
                .map(bean -> {

                    AgentDefinition annotation = bean.getClass().getAnnotation(AgentDefinition.class);

                    return Agent.builder()
                            .name(annotation.name())
                            .systemPrompt(annotation.systemPrompt())
                            .requiredTools(List.of(annotation.tools()))
                            .build();
                }).toList();
    }
}
