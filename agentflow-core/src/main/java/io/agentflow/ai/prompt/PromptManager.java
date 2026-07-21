package io.agentflow.ai.prompt;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class PromptManager {

    private final PromptLoader promptLoader;

    private final Map<String, PromptTemplate> templates = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        promptLoader.load().forEach(this::register);
    }

    public void register(PromptTemplate template) {
        templates.put(template.getName(), template);
    }

    public PromptTemplate get(String name) {
        return templates.get(name);
    }

    public boolean contains(String name) {
        return templates.containsKey(name);
    }

    public int size() {
        return templates.size();
    }

    public Collection<PromptTemplate> getAll() {
        return templates.values();
    }

}
