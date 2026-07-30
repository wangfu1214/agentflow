package io.agentflow.agent.discovery;

import io.agentflow.agent.AgentRegistry;
import io.agentflow.agent.DefaultAgentRegistry;

import java.util.Collection;
import java.util.Objects;

public class DefaultAgentRegistryBootstrap implements AgentRegistryBootstrap {

    private final AgentDefinitionRegistrar registrar;

    public DefaultAgentRegistryBootstrap(AgentDefinitionRegistrar registrar) {
        this.registrar =
                Objects.requireNonNull(registrar, "registrar must not be null");
    }

    @Override
    public AgentRegistry initialize(Collection<Class<?>> agentClasses) {
        Objects.requireNonNull(agentClasses, "agentClasses must not be null");
        AgentRegistry registry = new DefaultAgentRegistry();
        registrar.register(agentClasses, registry);
        return registry;
    }
}
