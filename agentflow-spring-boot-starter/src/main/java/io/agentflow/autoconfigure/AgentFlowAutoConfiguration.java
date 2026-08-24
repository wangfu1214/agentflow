package io.agentflow.autoconfigure;

import io.agentflow.agent.AgentExecutionFactory;
import io.agentflow.agent.AgentRegistry;
import io.agentflow.agent.DefaultAgentExecutionFactory;
import io.agentflow.agent.DefaultAgentRegistry;
import io.agentflow.autoconfigure.agent.AgentRegistrar;
import io.agentflow.autoconfigure.agent.AgentScanner;
import io.agentflow.autoconfigure.agent.SpringAgentDefinitionScanner;
import io.agentflow.autoconfigure.agent.SpringAgentRegistrar;
import io.agentflow.autoconfigure.extension.ExtensionRegistrar;
import io.agentflow.autoconfigure.extension.ExtensionScanner;
import io.agentflow.client.AgentFlow;
import io.agentflow.client.DefaultAgentFlow;
import io.agentflow.client.DefaultExecutionFactory;
import io.agentflow.execution.*;
import io.agentflow.execution.environment.DefaultExecutionEnvironment;
import io.agentflow.execution.environment.ExecutionEnvironment;
import io.agentflow.execution.interceptor.ExecutionInterceptorChain;
import io.agentflow.execution.lifecycle.NoopExecutionLifecycle;
import io.agentflow.execution.pipeline.DefaultExecutionPipeline;
import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.pipeline.ModelExecutionStep;
import io.agentflow.execution.result.DefaultExecutionResultHandler;
import io.agentflow.extension.ExtensionRegistry;
import io.agentflow.model.ModelInvoker;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnProperty(
        prefix = "agentflow",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties(AgentFlowProperties.class)
public class AgentFlowAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(ExecutionEngine.class)
    public AgentFlow agentFlow(AgentRegistry agentRegistry,
                               AgentExecutionFactory agentExecutionFactory,
                               ExecutionEngine executionEngine) {
        return new DefaultAgentFlow(agentRegistry, agentExecutionFactory, executionEngine);
    }

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
    @ConditionalOnMissingBean
    @ConditionalOnBean(ExecutionEnvironment.class)
    public ExecutionEngine executionEngine(ExecutionEnvironment environment) {
        return new DefaultExecutionEngine(environment);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExecutionFactory executionFactory(ExecutionIdGenerator executionIdGenerator) {
        return new DefaultExecutionFactory(executionIdGenerator);
    }


    @Bean
    @ConditionalOnMissingBean
    public ExecutionIdGenerator executionIdGenerator() {
        return new UuidExecutionIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public AgentExecutionFactory agentExecutionFactory(ExecutionFactory executionFactory) {
        return new DefaultAgentExecutionFactory(executionFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    AgentRegistry agentRegistry() {
        return new DefaultAgentRegistry();
    }

    @Bean
    AgentScanner agentScanner() {
        return new AgentScanner();
    }

    @Bean
    AgentRegistrar agentRegistrar() {
        return new AgentRegistrar();
    }

    @Bean
    SpringAgentDefinitionScanner springAgentDefinitionScanner(ApplicationContext context) {
        return new SpringAgentDefinitionScanner(context);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(ModelInvoker.class)
    public ExecutionEnvironment executionEnvironment(ModelInvoker modelInvoker) {
        ExecutionPipeline pipeline = new DefaultExecutionPipeline(List.of(new ModelExecutionStep(modelInvoker)));

        return new DefaultExecutionEnvironment(new DefaultExecutionContextFactory(),
                new NoopExecutionLifecycle(),
                new ExecutionInterceptorChain(List.of()),
                pipeline,
                new DefaultExecutionResultHandler());
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringAgentRegistrar springAgentRegistrar(AgentRegistry agentRegistry) {
        return new SpringAgentRegistrar(agentRegistry);
    }

    @Bean
    public SmartInitializingSingleton agentRegistrationInitializer(SpringAgentDefinitionScanner scanner, SpringAgentRegistrar registrar) {
        return () -> registrar.register(scanner.scan());
    }
}
