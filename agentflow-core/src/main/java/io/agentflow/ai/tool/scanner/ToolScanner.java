package io.agentflow.ai.tool.scanner;

import io.agentflow.ai.tool.ToolDefinition;
import io.agentflow.ai.tool.ToolMetadata;
import io.agentflow.ai.tool.annotation.AgentTool;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ToolScanner {

    private final ApplicationContext applicationContext;

    public List<ToolDefinition> scan() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(AgentTool.class);
        return beans.values()
                .stream()
                .map(this::toToolDefinition)
                .toList();
    }

    private ToolDefinition toToolDefinition(Object bean) {
        Class<?> beanClass = AopUtils.getTargetClass(bean);

        AgentTool annotation = beanClass.getAnnotation(AgentTool.class);

        ToolMetadata metadata = ToolMetadata.builder()
                .name(annotation.name())
                .description(annotation.description())
                .category(annotation.category())
                .enabled(annotation.enabled())
                .build();
        return ToolDefinition.builder()
                .toolMetadata(metadata)
                .bean(bean)
                .beanClass(beanClass)
                .build();
    }
}
