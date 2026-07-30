package io.agentflow.tool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DefaultToolRegistry implements ToolRegistry {

    private final Map<String, ToolBinding> tools = new ConcurrentHashMap<>();

    @Override
    public void register(ToolBinding binding) {
        tools.put(binding.definition().name(), binding);
    }

    @Override
    public ToolBinding get(String name) {
        ToolBinding binding = tools.get(name);
        if (binding == null) {
            throw new IllegalArgumentException("tool not found: " + name);
        }
        return binding;
    }
}
