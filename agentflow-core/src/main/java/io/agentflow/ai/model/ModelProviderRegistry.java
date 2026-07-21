package io.agentflow.ai.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModelProviderRegistry {

    private final Map<String, ModelProvider> providers = new ConcurrentHashMap<>();

    public void register(ModelProvider provider) {
        providers.put(provider.name(), provider);
    }

    public ModelProvider get(String name) {
        return providers.get(name);
    }

    public boolean contains(String name) {
        return providers.containsKey(name);
    }
}
