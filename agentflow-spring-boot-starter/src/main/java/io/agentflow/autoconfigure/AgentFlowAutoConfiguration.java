package io.agentflow.autoconfigure;

import io.agentflow.ai.model.ModelProviderRegistry;
import io.agentflow.ai.model.ModelRouter;
import io.agentflow.autoconfigure.extension.ExtensionRegistrar;
import io.agentflow.autoconfigure.extension.ExtensionScanner;
import io.agentflow.autoconfigure.model.ModelProviderRegistryInitializer;
import io.agentflow.extension.ExtensionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = "agentflow",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties(AgentFlowProperties.class)
@ComponentScan(basePackages = "com.wangfu.agentflow.ai")
public class AgentFlowAutoConfiguration {

    @Bean
    public ExtensionRegistry extensionRegistry() {
        return new ExtensionRegistry();
    }

    @Bean
    public ExtensionScanner extensionScanner(ApplicationContext applicationContext) {
        return new ExtensionScanner(applicationContext);
    }

    @Bean
    public ExtensionRegistrar extensionRegistrar(ExtensionScanner scanner, ExtensionRegistry extensionRegistry) {
        return new ExtensionRegistrar(scanner, extensionRegistry);
    }

    @Bean
    public ModelProviderRegistry modelProviderRegistry() {
        return new ModelProviderRegistry();
    }

    @Bean
    public ModelProviderRegistryInitializer modelProviderRegistryInitializer(
            ExtensionRegistry extensionRegistry,
            ModelProviderRegistry modelProviderRegistry) {

        return new ModelProviderRegistryInitializer(
                extensionRegistry,
                modelProviderRegistry
        );
    }

    @Bean
    public ModelRouter modelRouter(ModelProviderRegistry registry) {
        return new ModelRouter(registry);
    }
}
