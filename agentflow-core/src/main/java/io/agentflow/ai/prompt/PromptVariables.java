package io.agentflow.ai.prompt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PromptVariables {


    private final Map<String, Object> values = new HashMap<>();

    private PromptVariables() {
    }

    public static PromptVariables create() {
        return new PromptVariables();
    }

    public PromptVariables add(String key, Object value) {
        values.put(key, value);
        return this;
    }

    public Object get(String key) {
        return values.get(key);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Map<String, Object> asMap() {
        return Collections.unmodifiableMap(values);
    }
}
