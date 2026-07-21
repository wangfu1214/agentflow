package io.agentflow.autoconfigure.model;

import io.agentflow.ai.model.ModelProvider;
import io.agentflow.ai.model.ModelProviderRegistry;
import io.agentflow.extension.ExtensionRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class ModelProviderRegistryInitializer {

    private final ExtensionRegistry extensionRegistry;

    private final ModelProviderRegistry modelProviderRegistry;

    @PostConstruct
    public void init() {
        Collection<Object> extensions = extensionRegistry.getAll();

        extensions.stream()
                .filter(ModelProvider.class::isInstance)
                .map(ModelProvider.class::cast)
                .forEach(modelProviderRegistry::register);
    }
}
