package io.agentflow.agent;

import java.util.List;
import java.util.Objects;

/**
 * Immutable definition describing how an AI agent should behave.
 */
public final class Agent {

    private final String name;

    private final String systemPrompt;

    private final List<String> requiredTools;

    private Agent(Builder builder) {
        this.name = requireNotBlank(
                builder.name,
                "name");
        this.systemPrompt = requireNotBlank(
                builder.systemPrompt,
                "systemPrompt");
        this.requiredTools =
                List.copyOf(
                        builder.requiredTools
                );
    }

    public static Builder builder() {
        return new Builder();
    }

    public String name() {
        return name;
    }

    public String systemPrompt() {
        return systemPrompt;
    }

    public List<String> requiredTools () {
        return requiredTools;
    }

    private static String requireNotBlank(String value, String fieldName) {
        Objects.requireNonNull(value,
                fieldName + " must not be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }

    public static final class Builder {

        private String name;

        private String systemPrompt;

        private List<String> requiredTools;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder systemPrompt(String systemPrompt) {
            this.systemPrompt = systemPrompt;
            return this;
        }

        public Builder requiredTools(List<String> tools) {
            this.requiredTools =
                    Objects.requireNonNull(tools, "requiredTools must not be null");
            return this;
        }

        public Agent build() {
            return new Agent(this);
        }
    }
}
