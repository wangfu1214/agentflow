package io.agentflow.agent;


import java.util.List;
import java.util.Objects;


/**
 * Immutable definition of an agent.
 *
 * AgentDefinition describes what an agent is,
 * not how it is executed.
 */
public final class AgentDefinition {


    private final String id;


    private final String name;


    private final String description;


    private final String systemPrompt;


    private final List<String> requiredTools;



    public AgentDefinition(
            String id,
            String name,
            String description,
            String systemPrompt,
            List<String> requiredTools) {

        this.id = requireNotBlank(id, "id");
        this.name = requireNotBlank(name, "name");
        this.description = description == null ? "" : description;
        this.systemPrompt = requireNotBlank(systemPrompt, "systemPrompt");
        this.requiredTools =
                List.copyOf(Objects.requireNonNull(
                        requiredTools, "requiredTools must not be null"));
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String systemPrompt() {
        return systemPrompt;
    }

    public List<String> requiredTools() {
        return requiredTools;
    }

    private static String requireNotBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        if(value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }

}