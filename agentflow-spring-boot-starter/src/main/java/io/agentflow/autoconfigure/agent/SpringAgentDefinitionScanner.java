package io.agentflow.autoconfigure.agent;

import io.agentflow.agent.Agent;
import io.agentflow.agent.annotation.AgentDefinition;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpringAgentDefinitionScanner {

    private final ApplicationContext applicationContext;

    public SpringAgentDefinitionScanner(ApplicationContext applicationContext) {
        this.applicationContext =
                Objects.requireNonNull(applicationContext, "applicationContext must not be null");
    }

    public List<Agent> scan() {
        Map<String, Object> beans =
                applicationContext.getBeansWithAnnotation(AgentDefinition.class);

        return beans.values()
                .stream()
                .map(this::toAgent)
                .toList();
    }

    private Agent toAgent(Object bean) {
        Class<?> targetClass =
                AopUtils.getTargetClass(bean);

        AgentDefinition definition = AnnotationUtils.findAnnotation(targetClass, AgentDefinition.class);

        if (definition == null) {
            throw new IllegalStateException("AgentDefinition not found on " + targetClass.getName());
        }

        return Agent.builder()
                .name(definition.name())
                .systemPrompt(definition.systemPrompt())
                .requiredTools(List.of(definition.tools()))
                .build();
    }
}