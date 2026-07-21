package io.agentflow.ai.tool.registry;

import io.agentflow.ai.tool.ToolDefinition;
import io.agentflow.ai.tool.scanner.ToolScanner;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ToolRegistry {

    private final ToolScanner toolScanner;

    private final Map<String, ToolDefinition> toolDefinitions = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        Map<String, ToolDefinition> definitionMap = toolScanner.scan()
                .stream()
                .collect(Collectors.toMap(
                        d -> d.getToolMetadata().getName(),
                        Function.identity()));
        toolDefinitions.putAll(definitionMap);
    }


    /**
     * 获取指定名称的tool工具定义
     *
     * @param name tool名称
     * @return ToolDefinition
     */
    public ToolDefinition get(String name) {
        return toolDefinitions.get(name);
    }

    /**
     *
     * 获取所有 AI 工具集
     *
     * @return Collection<ToolDefinition>
     */
    public Collection<ToolDefinition> getAll() {
        return toolDefinitions.values();
    }


    public boolean contains(String name) {
        return toolDefinitions.containsKey(name);
    }

    public int size() {
        return toolDefinitions.size();
    }

    public Object[] getAllTools() {
        return toolDefinitions.values().stream()
                .map(ToolDefinition::getBean)
                .toArray();
    }
}
