package io.agentflow.extension;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtensionRegistry {

    private final Map<String, Object> extensions = new ConcurrentHashMap<>();

    public void register(String name, Object object) {
        extensions.put(name, object);
    }

    public Object get(String name) {
        return extensions.get(name);
    }

    public Collection<Object> getAll() {
        return extensions.values();
    }

    public boolean contains(String name) {
        return extensions.containsKey(name);
    }

    public int size() {
        return extensions.size();
    }
}
