package io.agentflow.ai.agent.scanner;

import io.agentflow.ai.agent.annotation.Agent;
import io.agentflow.ai.agent.factory.AgentFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AgentScanner {

    private final AgentFactory agentFactory;

    private final ApplicationContext applicationContext;

    public List<io.agentflow.ai.agent.Agent> scan() {
        Map<String, Object> beans = 
                applicationContext.getBeansWithAnnotation(Agent.class);
        
        return beans.values().stream()
                .map(this::toAgent)
                .toList();
    }

    private io.agentflow.ai.agent.Agent toAgent(Object bean) {

        Class<?> beanClass = AopUtils.getTargetClass(bean);

        Agent annotation = beanClass.getAnnotation(Agent.class);

        return agentFactory.create(annotation);
    }
}
