package io.agentflow.agent.discovery;

import io.agentflow.agent.Agent;
import io.agentflow.agent.AgentRegistry;

import java.util.Collection;
import java.util.Objects;

public class DefaultAgentDefinitionRegistrar implements AgentDefinitionRegistrar {

    private final AgentDefinitionScanner scanner;


    public DefaultAgentDefinitionRegistrar(AgentDefinitionScanner scanner) {
        this.scanner = Objects.requireNonNull(scanner);
    }

    @Override
    public void register(Collection<Class<?>> classes, AgentRegistry registry) {
        Objects.requireNonNull(classes);
        Objects.requireNonNull(registry);

        Collection<Agent> agents = scanner.scan(classes);

        for (Agent agent : agents) {
            registry.register(agent);
        }
    }
}
