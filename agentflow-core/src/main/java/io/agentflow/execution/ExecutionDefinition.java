package io.agentflow.execution;

import java.util.List;
import java.util.Objects;

/**
 * Immutable snapshot of the definition and input used by one execution.
 *
 * @param agentName    name of the agent used for the execution
 * @param systemPrompt system instructions captured for the execution
 * @param input        caller input captured for the execution
 */
public record ExecutionDefinition(String agentName,
                                  String systemPrompt,
                                  String input,
                                  List<String> requiredTools) {

    public ExecutionDefinition {
        agentName = requireNotBlank(agentName, "agentName");
        systemPrompt = requireNotBlank(systemPrompt, "systemPrompt");
        input = requireNotBlank(input, "input");
        Objects.requireNonNull(requiredTools, "requiredTools must not be null");
        requiredTools = List.copyOf(requiredTools);
    }

    private static String requireNotBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
