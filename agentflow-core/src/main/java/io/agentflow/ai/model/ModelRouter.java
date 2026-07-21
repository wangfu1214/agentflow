package io.agentflow.ai.model;

/**
 * 模型路由
 */
public class ModelRouter {

    private final ModelProviderRegistry modelProviderRegistry;

    public ModelRouter(ModelProviderRegistry modelProviderRegistry) {
        this.modelProviderRegistry = modelProviderRegistry;
    }

    public ModelInvoker route(String providerName) {
        ModelProvider modelProvider = modelProviderRegistry.get(providerName);
        if (modelProvider == null) {
            throw new IllegalStateException(
                    "Model provider not found: "
                            + providerName
            );
        }
        return modelProvider.createInvoker();
    }
}
