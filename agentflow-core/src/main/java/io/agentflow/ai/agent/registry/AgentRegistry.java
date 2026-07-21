package io.agentflow.ai.agent.registry;

import io.agentflow.ai.agent.Agent;
import io.agentflow.ai.agent.scanner.AgentScanner;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class AgentRegistry {

    private final AgentScanner agentScanner;

    private final Map<String, Agent> agents = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        agentScanner.scan().forEach(this::register);
    }

    public void register(Agent agent) {
        if (agents.containsKey(agent.getName())) {
            throw new IllegalStateException(
                    "Duplicate agent: " + agent.getName());
        }
        agents.put(agent.getName(), agent);
    }

    public Agent get(String name) {
        return agents.get(name);
    }

    public Collection<Agent> getAll() {
        return agents.values();
    }

    public boolean contains(String name) {
        return agents.containsKey(name);
    }

    public int size() {
        return agents.size();
    }
}
